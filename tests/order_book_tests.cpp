#include "orderbook/order_book.hpp"

#include <cassert>
#include <iostream>
#include <stdexcept>

int main() {
    {
        OrderBook book;
        book.process({1, 10000, 10, Side::Sell});
        auto trades = book.process({2, 10000, 5, Side::Buy});
        assert(trades.size() == 1);
        assert(trades[0].quantity == 5);
        assert(book.order_count() == 1);
    }
    {
        OrderBook book;
        book.process({1, 10000, 5, Side::Sell});
        book.process({2, 10000, 5, Side::Sell});
        auto trades = book.process({3, 10000, 7, Side::Buy});
        assert(trades.size() == 2);
        assert(trades[0].sell_order_id == 1);
        assert(trades[1].sell_order_id == 2);
        assert(trades[0].quantity == 5 && trades[1].quantity == 2);
        assert(book.order_count() == 1);
    }
    {
        OrderBook book;
        book.process({1, 10000, 5, Side::Buy});
        book.process({2, 10100, 5, Side::Buy});
        assert(book.cancel(1));
        assert(!book.cancel(1));
        auto trades = book.process({3, 10000, 5, Side::Sell});
        assert(trades.size() == 1);
        assert(trades[0].buy_order_id == 2);
        assert(trades[0].price_ticks == 10100);
    }
    {
        OrderBook book;
        book.process({1, 10000, 5, Side::Buy});
        auto trades = book.process({2, 9900, 5, Side::Sell});
        assert(trades.size() == 1);
        assert(trades[0].price_ticks == 10000);
        assert(book.order_count() == 0);
    }
    {
        OrderBook book;
        book.process({1, 10000, 5, Side::Buy});
        bool rejected = false;
        try {
            book.process({1, 10100, 5, Side::Buy});
        } catch (const std::invalid_argument&) {
            rejected = true;
        }
        assert(rejected);
        assert(book.order_count() == 1);
    }
    std::cout << "All order-book tests passed.\n";
}
