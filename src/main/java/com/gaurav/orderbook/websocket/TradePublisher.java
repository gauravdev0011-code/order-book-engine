package com.gaurav.orderbook.websocket;

import com.gaurav.orderbook.model.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradePublisher {

    private static final String TRADE_TOPIC = "/topic/trades";

    private final SimpMessagingTemplate template;

    /**
     * Publish executed trade to websocket subscribers
     */
    public void publish(Trade trade) {

        if (trade == null) {
            log.warn("Attempted to publish null trade");
            return;
        }

        try {

            template.convertAndSend(TRADE_TOPIC, trade);

            log.info(
                    "Published trade | id={} price={} quantity={}",
                    trade.getTradeId(),
                    trade.getPrice(),
                    trade.getQuantity()
            );

        } catch (Exception ex) {

            log.error(
                    "Failed to publish trade | id={}",
                    trade.getTradeId(),
                    ex
            );
        }
    }
}