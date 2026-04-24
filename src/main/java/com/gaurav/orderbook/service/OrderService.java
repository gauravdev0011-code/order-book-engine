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
    private final TradeService tradeService;

    public OrderService(TradePublisher publisher, TradeService tradeService) {
        this.publisher = publisher;
        this.tradeService = tradeService;
    }

    public List<Trade> process(Order order) {

        List<Trade> trades = engine.processOrder(order);

        for (Trade trade : trades) {
            tradeService.handle(trade);   // ✅ now used
            publisher.publish(trade);     // ✅ real-time
        }

        return trades;
    }
}