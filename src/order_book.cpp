#include "orderbook/order_book.hpp"

#include <algorithm>
#include <stdexcept>
#include <utility>

void OrderBook::validate(const Order& order) {
    if (order.id == 0) throw std::invalid_argument("order id must be non-zero");
    if (order.price_ticks <= 0) throw std::invalid_argument("price must be positive");
    if (order.quantity == 0) throw std::invalid_argument("quantity must be positive");
}

void OrderBook::add_resting(Order order) {
    if (order.side == Side::Buy) {
        auto [level, ignored] = bids_.try_emplace(order.price_ticks);
        level->second.push_back(std::move(order));
        auto it = std::prev(level->second.end());
        index_.emplace(it->id, Location{Side::Buy, level->first, it});
    } else {
        auto [level, ignored] = asks_.try_emplace(order.price_ticks);
        level->second.push_back(std::move(order));
        auto it = std::prev(level->second.end());
        index_.emplace(it->id, Location{Side::Sell, level->first, it});
    }
}

std::vector<Trade> OrderBook::process(Order incoming) {
    validate(incoming);

    if (index_.contains(incoming.id)) {
        throw std::invalid_argument("order id already exists");
    }

    std::vector<Trade> trades;

    if (incoming.side == Side::Buy) {
        while (incoming.quantity > 0 && !asks_.empty()) {
            auto level = asks_.begin();
            if (incoming.price_ticks < level->first) break;

            auto& queue = level->second;
            auto resting = queue.begin();
            const auto fill = std::min(incoming.quantity, resting->quantity);
            trades.push_back(
                Trade{incoming.id, resting->id, resting->price_ticks, fill}
            );
            incoming.quantity -= fill;
            resting->quantity -= fill;

            if (resting->quantity == 0) {
                index_.erase(resting->id);
                queue.erase(resting);
            }
            if (queue.empty()) asks_.erase(level);
        }
    } else {
        while (incoming.quantity > 0 && !bids_.empty()) {
            auto level = bids_.begin();
            if (incoming.price_ticks > level->first) break;

            auto& queue = level->second;
            auto resting = queue.begin();
            const auto fill = std::min(incoming.quantity, resting->quantity);
            trades.push_back(
                Trade{resting->id, incoming.id, resting->price_ticks, fill}
            );
            incoming.quantity -= fill;
            resting->quantity -= fill;

            if (resting->quantity == 0) {
                index_.erase(resting->id);
                queue.erase(resting);
            }
            if (queue.empty()) bids_.erase(level);
        }
    }

    if (incoming.quantity > 0) add_resting(std::move(incoming));
    return trades;
}

bool OrderBook::cancel(std::uint64_t order_id) {
    auto found = index_.find(order_id);
    if (found == index_.end()) return false;

    const auto location = found->second;
    if (location.side == Side::Buy) {
        auto level = bids_.find(location.price);
        if (level == bids_.end()) return false;
        level->second.erase(location.it);
        if (level->second.empty()) bids_.erase(level);
    } else {
        auto level = asks_.find(location.price);
        if (level == asks_.end()) return false;
        level->second.erase(location.it);
        if (level->second.empty()) asks_.erase(level);
    }
    index_.erase(found);
    return true;
}
