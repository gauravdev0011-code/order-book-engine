package com.gaurav.orderbook.service;

import com.gaurav.orderbook.model.Trade;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    public void handle(Trade trade) {
        // For now, just log (proves usage)
        System.out.println("Trade executed: " + trade);
    }
}