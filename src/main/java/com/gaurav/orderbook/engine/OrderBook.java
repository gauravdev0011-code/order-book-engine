package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Side;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Central in-memory order book.
 *
 * Bids:
 * - highest price first
 *
 * Asks:
 * - lowest price first
 */
public class OrderBook {

    private final NavigableMap<Double, OrderQueue> bids =
            new TreeMap<>(Collections.reverseOrder());

    private final NavigableMap<Double, OrderQueue> asks =
            new TreeMap<>();

    /**
     * Get bid side of book
     */
    public NavigableMap<Double, OrderQueue> getBids() {
        return Collections.unmodifiableNavigableMap(bids);
    }

    /**
     * Get ask side of book
     */
    public NavigableMap<Double, OrderQueue> getAsks() {
        return Collections.unmodifiableNavigableMap(asks);
    }

    /**
     * Add order to correct side of book
     */
    public void addOrder(Order order) {

        validateOrder(order);

        NavigableMap<Double, OrderQueue> book =
                (order.getSide() == Side.BUY)
                        ? bids
                        : asks;

        book.computeIfAbsent(
                order.getPrice(),
                price -> new OrderQueue()
        ).add(order);
    }

    /**
     * Remove empty price levels
     */
    public void removePriceLevelIfEmpty(
            Side side,
            double price
    ) {

        NavigableMap<Double, OrderQueue> book =
                (side == Side.BUY)
                        ? bids
                        : asks;

        OrderQueue queue = book.get(price);

        if (queue != null && queue.isEmpty()) {
            book.remove(price);
        }
    }

    /**
     * Validate incoming order
     */
    private void validateOrder(Order order) {

        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null"
            );
        }

        if (order.getPrice() <= 0) {
            throw new IllegalArgumentException(
                    "Price must be positive"
            );
        }

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive"
            );
        }
    }
}