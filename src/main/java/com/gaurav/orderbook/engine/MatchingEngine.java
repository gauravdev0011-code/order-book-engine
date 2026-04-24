package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.*;
import com.gaurav.orderbook.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

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

            double price = orderBook.getAsks().firstKey();
            OrderQueue queue = orderBook.getAsks().get(price);
            Order sellOrder = queue.peek();

            int qty = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

            trades.add(createTrade(buyOrder, sellOrder, price, qty));

            buyOrder.setQuantity(buyOrder.getQuantity() - qty);
            sellOrder.setQuantity(sellOrder.getQuantity() - qty);

            queue.removeIfFilled();

            if (queue.isEmpty()) {
                orderBook.getAsks().remove(price);
            }
        }
    }

    private void matchSell(Order sellOrder, List<Trade> trades) {

        while (!orderBook.getBids().isEmpty()
                && sellOrder.getQuantity() > 0
                && sellOrder.getPrice() <= orderBook.getBids().firstKey()) {

            double price = orderBook.getBids().firstKey();
            OrderQueue queue = orderBook.getBids().get(price);
            Order buyOrder = queue.peek();

            int qty = Math.min(sellOrder.getQuantity(), buyOrder.getQuantity());

            trades.add(createTrade(buyOrder, sellOrder, price, qty));

            sellOrder.setQuantity(sellOrder.getQuantity() - qty);
            buyOrder.setQuantity(buyOrder.getQuantity() - qty);

            queue.removeIfFilled();

            if (queue.isEmpty()) {
                orderBook.getBids().remove(price);
            }
        }
    }

    private Trade createTrade(Order buy, Order sell, double price, int qty) {

        Trade trade = new Trade();
        trade.setTradeId(IdGenerator.generateId());
        trade.setBuyOrderId(buy.getId());
        trade.setSellOrderId(sell.getId());
        trade.setPrice(price);
        trade.setQuantity(qty);
        trade.setTimestamp(System.currentTimeMillis());

        return trade;
    }
}