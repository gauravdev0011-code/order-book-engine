package com.gaurav.orderbook.service;

import com.gaurav.orderbook.engine.MatchingEngine;
import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import com.gaurav.orderbook.websocket.TradePublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final MatchingEngine engine = new MatchingEngine();
    private final TradePublisher publisher;

    public OrderService(TradePublisher publisher) {
        this.publisher = publisher;
    }

    public List<Trade> process(Order order) {

        List<Trade> trades = engine.processOrder(order);

        for (Trade trade : trades) {
            publisher.publish(trade);
        }

        return trades;
    }
}