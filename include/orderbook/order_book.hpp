#pragma once

#include "order.hpp"
#include <cstddef>
#include <cstdint>
#include <list>
#include <map>
#include <optional>
#include <unordered_map>
#include <vector>

class OrderBook {
public:
    using Price = std::int64_t;
    using Queue = std::list<Order>;

    struct Location {
        Side side;
        Price price;
        Queue::iterator it;
    };

    std::vector<Trade> process(Order incoming);
    bool cancel(std::uint64_t order_id);

    std::size_t order_count() const noexcept { return index_.size(); }
    std::size_t bid_levels() const noexcept { return bids_.size(); }
    std::size_t ask_levels() const noexcept { return asks_.size(); }

private:
    using BidBook = std::map<Price, Queue, std::greater<Price>>;
    using AskBook = std::map<Price, Queue>;

    BidBook bids_;
    AskBook asks_;
    std::unordered_map<std::uint64_t, Location> index_;

    static void validate(const Order& order);
    void add_resting(Order order);
};
