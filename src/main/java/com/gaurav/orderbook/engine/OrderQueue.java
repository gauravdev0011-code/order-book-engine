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

    public Order poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}