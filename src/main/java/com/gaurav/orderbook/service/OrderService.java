package com.gaurav.orderbook.service;

import com.gaurav.orderbook.engine.MatchingEngine;
import com.gaurav.orderbook.model.Order;
import com.gaurav.orderbook.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private MatchingEngine matchingEngine;

    public List<Trade> processOrder(Order order) {
        return matchingEngine.processOrder(order);
    }
}