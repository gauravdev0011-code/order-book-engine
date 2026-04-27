package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.*;
import com.gaurav.orderbook.util.IdGenerator;
import com.gaurav.orderbook.websocket.TradePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MatchingEngine {

    private final OrderBook orderBook = new OrderBook();

    @Autowired
    private TradePublisher tradePublisher;

    public List<Trade> processOrder(Order incomingOrder) {

        List<Trade> trades = new ArrayList<>();

        if (incomingOrder.getSide() == Side.BUY) {
            match(incomingOrder, orderBook.getAsks(), true, trades);
        } else {
            match(incomingOrder, orderBook.getBids(), false, trades);
        }

        // If not fully matched → add to book
        if (incomingOrder.getQuantity() > 0) {
            orderBook.addOrder(incomingOrder);
        }

        return trades;
    }

    private void match(Order incoming,
                       TreeMap<Double, OrderQueue> book,
                       boolean isBuy,
                       List<Trade> trades) {

        while (!book.isEmpty() && incoming.getQuantity() > 0) {

            double bestPrice = book.firstKey();

            // Stop if price condition not satisfied
            if ((isBuy && incoming.getPrice() < bestPrice) ||
                    (!isBuy && incoming.getPrice() > bestPrice)) {
                break;
            }

            OrderQueue queue = book.get(bestPrice);
            Order opposite = queue.peek();

            int qty = Math.min(incoming.getQuantity(), opposite.getQuantity());

            Trade trade;
            if (isBuy) {
                trade = createTrade(incoming, opposite, bestPrice, qty);
            } else {
                trade = createTrade(opposite, incoming, bestPrice, qty);
            }

            // Add + publish
            trades.add(trade);
            tradePublisher.publish(trade);

            // Reduce quantities
            incoming.setQuantity(incoming.getQuantity() - qty);
            opposite.setQuantity(opposite.getQuantity() - qty);

            // Clean queue
            queue.removeIfFilled();

            if (queue.isEmpty()) {
                book.remove(bestPrice);
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