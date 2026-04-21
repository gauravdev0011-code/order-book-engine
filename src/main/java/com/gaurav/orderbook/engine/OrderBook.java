package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Side;

import java.util.*;

public class OrderBook {

    private final TreeMap<Double, Queue<Order>> bids =
            new TreeMap<>(Collections.reverseOrder());

    private final TreeMap<Double, Queue<Order>> asks =
            new TreeMap<>();

    public TreeMap<Double, Queue<Order>> getBids() {
        return bids;
    }

    public TreeMap<Double, Queue<Order>> getAsks() {
        return asks;
    }

    public void addOrder(Order order) {
        TreeMap<Double, Queue<Order>> book =
                (order.getSide() == Side.BUY) ? bids : asks;

        book.putIfAbsent(order.getPrice(), new LinkedList<>());
        book.get(order.getPrice()).add(order);
    }
}