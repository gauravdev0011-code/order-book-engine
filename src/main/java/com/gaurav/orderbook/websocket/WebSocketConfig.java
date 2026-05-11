package com.gaurav.orderbook.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String WS_ENDPOINT = "/ws";
    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:5173"
    };

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        log.info("Registering websocket endpoint: {}", WS_ENDPOINT);

        registry.addEndpoint(WS_ENDPOINT)

                // Restrict origins in production
                .setAllowedOrigins(ALLOWED_ORIGINS)

                // Enable SockJS fallback
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        log.info("Configuring websocket message broker");

        // Client subscription topics
        registry.enableSimpleBroker("/topic")

                // Heartbeat support
                .setHeartbeatValue(new long[]{10000, 10000});

        // Incoming app destinations
        registry.setApplicationDestinationPrefixes("/app");
    }
}