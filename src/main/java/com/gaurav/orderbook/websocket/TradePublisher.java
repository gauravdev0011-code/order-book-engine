package com.gaurav.orderbook.websocket;

import com.gaurav.orderbook.model.Trade;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TradePublisher {

    private final SimpMessagingTemplate template;

    public TradePublisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publish(Trade trade) {
        template.convertAndSend("/topic/trades", trade);
    }
}