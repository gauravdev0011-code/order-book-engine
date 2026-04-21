package com.gaurav.orderbook.service;

import com.gaurav.orderbook.engine.MatchingEngine;
import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final MatchingEngine engine = new MatchingEngine();

    public List<Trade> process(Order order) {
        return engine.processOrder(order);
    }
}