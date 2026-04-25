package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Side;
import lombok.Getter;

import java.util.TreeMap;

@Getter
public class OrderBook {

    private final TreeMap<Double, OrderQueue> bids = new TreeMap<>((a, b) -> Double.compare(b, a)); // max heap
    private final TreeMap<Double, OrderQueue> asks = new TreeMap<>(); // min heap

    public void addOrder(Order order) {

        TreeMap<Double, OrderQueue> book =
                (order.getSide() == Side.BUY) ? bids : asks;

        book.putIfAbsent(order.getPrice(), new OrderQueue());
        book.get(order.getPrice()).add(order);
    }
}