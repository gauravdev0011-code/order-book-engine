package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Side;

import java.util.Collections;
import java.util.TreeMap;

public class OrderBook {

    private final TreeMap<Double, OrderQueue> bids =
            new TreeMap<>(Collections.reverseOrder());

    private final TreeMap<Double, OrderQueue> asks =
            new TreeMap<>();

    public TreeMap<Double, OrderQueue> getBids() {
        return bids;
    }

    public TreeMap<Double, OrderQueue> getAsks() {
        return asks;
    }

    public void addOrder(Order order) {

        TreeMap<Double, OrderQueue> book =
                (order.getSide() == Side.BUY) ? bids : asks;

        book.putIfAbsent(order.getPrice(), new OrderQueue());
        book.get(order.getPrice()).add(order);
    }
}