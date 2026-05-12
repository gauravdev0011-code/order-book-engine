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

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Publish executed trade to websocket subscribers
     */
    public void publish(Trade trade) {

        if (trade == null) {
            log.warn("Skipped websocket publish because trade was null");
            return;
        }

        try {

            messagingTemplate.convertAndSend(TRADE_TOPIC, trade);

            // Debug-level to avoid noisy production logs
            log.debug(
                    "Trade published | id={} price={} quantity={}",
                    trade.getTradeId(),
                    trade.getPrice(),
                    trade.getQuantity()
            );

        } catch (Exception ex) {

            log.error(
                    "WebSocket trade publish failed | id={} error={}",
                    trade.getTradeId(),
                    ex.getMessage(),
                    ex
            );
        }
    }
}