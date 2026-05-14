package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.Order;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * FIFO queue for orders at a specific price level.
 */
public class OrderQueue {

    private final Deque<Order> queue = new ArrayDeque<>();

    /**
     * Add order to queue
     */
    public void add(Order order) {

        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null"
            );
        }

        queue.offer(order);
    }

    /**
     * Peek next order without removing
     */
    public Order peek() {
        return queue.peekFirst();
    }

    /**
     * Remove filled orders automatically
     */
    public void removeIfFilled() {

        while (!queue.isEmpty()) {

            Order head = queue.peekFirst();

            if (head == null || head.getQuantity() > 0) {
                break;
            }

            queue.pollFirst();
        }
    }

    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Current queue depth
     */
    public int size() {
        return queue.size();
    }
}