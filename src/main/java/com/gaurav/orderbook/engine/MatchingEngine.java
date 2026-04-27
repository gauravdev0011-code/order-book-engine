package com.gaurav.orderbook.engine;

import com.gaurav.orderbook.model.*;
import com.gaurav.orderbook.util.IdGenerator;
import com.gaurav.orderbook.websocket.TradePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

        if (incomingOrder.getQuantity() > 0) {
            orderBook.addOrder(incomingOrder);
        }

        // 🔥 CRITICAL: publish trades
        for (Trade trade : trades) {
            tradePublisher.publish(trade);
        }

        return trades;
    }

    private void match(Order incoming,
                       TreeMap<Double, OrderQueue> book,
                       boolean isBuy,
                       List<Trade> trades) {

        while (!book.isEmpty() && incoming.getQuantity() > 0) {

            double bestPrice = book.firstKey();

            // price condition
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

            trades.add(trade);

            incoming.setQuantity(incoming.getQuantity() - qty);
            opposite.setQuantity(opposite.getQuantity() - qty);

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