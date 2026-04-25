package com.gaurav.orderbook.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers WebSocket endpoint for client connections.
     * SockJS is enabled for fallback support (useful for browser clients).
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // allow all origins (dev purpose)
                .withSockJS();
    }

    /**
     * Configures message routing:
     * - /topic → for broadcasting to clients (pub-sub)
     * - /app → for client-to-server communication
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");   // where clients subscribe
        registry.setApplicationDestinationPrefixes("/app"); // where clients send
    }
}