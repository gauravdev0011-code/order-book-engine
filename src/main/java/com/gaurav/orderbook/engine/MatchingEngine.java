package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.*;
import com.gaurav.orderbook.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MatchingEngine {

    private final OrderBook orderBook = new OrderBook();

    public List<Trade> processOrder(Order incomingOrder) {

        List<Trade> trades = new ArrayList<>();

        if (incomingOrder.getSide() == Side.BUY) {
            matchBuy(incomingOrder, trades);
        } else {
            matchSell(incomingOrder, trades);
        }

        if (incomingOrder.getQuantity() > 0) {
            orderBook.addOrder(incomingOrder);
        }

        return trades;
    }

    private void matchBuy(Order buyOrder, List<Trade> trades) {

        while (!orderBook.getAsks().isEmpty()
                && buyOrder.getQuantity() > 0
                && buyOrder.getPrice() >= orderBook.getAsks().firstKey()) {

            double bestPrice = orderBook.getAsks().firstKey();
            Queue<Order> queue = orderBook.getAsks().get(bestPrice);
            Order sellOrder = queue.peek();

            int qty = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

            trades.add(createTrade(buyOrder, sellOrder, bestPrice, qty));

            buyOrder.setQuantity(buyOrder.getQuantity() - qty);
            sellOrder.setQuantity(sellOrder.getQuantity() - qty);

            if (sellOrder.getQuantity() == 0) {
                queue.poll();
                if (queue.isEmpty()) {
                    orderBook.getAsks().remove(bestPrice);
                }
            }
        }
    }

    private void matchSell(Order sellOrder, List<Trade> trades) {

        while (!orderBook.getBids().isEmpty()
                && sellOrder.getQuantity() > 0
                && sellOrder.getPrice() <= orderBook.getBids().firstKey()) {

            double bestPrice = orderBook.getBids().firstKey();
            Queue<Order> queue = orderBook.getBids().get(bestPrice);
            Order buyOrder = queue.peek();

            int qty = Math.min(sellOrder.getQuantity(), buyOrder.getQuantity());

            trades.add(createTrade(buyOrder, sellOrder, bestPrice, qty));

            sellOrder.setQuantity(sellOrder.getQuantity() - qty);
            buyOrder.setQuantity(buyOrder.getQuantity() - qty);

            if (buyOrder.getQuantity() == 0) {
                queue.poll();
                if (queue.isEmpty()) {
                    orderBook.getBids().remove(bestPrice);
                }
            }
        }
    }

    private Trade createTrade(Order buy, Order sell, double price, int qty) {

        Trade t = new Trade();
        t.setTradeId(IdGenerator.generateId());
        t.setBuyOrderId(buy.getId());
        t.setSellOrderId(sell.getId());
        t.setPrice(price);
        t.setQuantity(qty);
        t.setTimestamp(System.currentTimeMillis());

        return t;
    }
}