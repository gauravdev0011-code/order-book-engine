package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.Order;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {

    private final Queue<Order> queue = new LinkedList<>();

    public void add(Order order) {
        queue.add(order);
    }

    public Order peek() {
        return queue.peek();
    }

    // Clean up filled orders automatically
    public void removeIfFilled() {
        while (!queue.isEmpty() && queue.peek().getQuantity() == 0) {
            queue.poll();
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}