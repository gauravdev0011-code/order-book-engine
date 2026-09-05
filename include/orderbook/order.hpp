#pragma once

#include <cstdint>

enum class Side : std::uint8_t { Buy, Sell };

struct Order {
    std::uint64_t id{};
    std::int64_t price_ticks{};
    std::uint32_t quantity{};
    Side side{};
};

struct Trade {
    std::uint64_t buy_order_id{};
    std::uint64_t sell_order_id{};
    std::int64_t price_ticks{};
    std::uint32_t quantity{};
};
