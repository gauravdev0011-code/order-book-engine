import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

/**
 * Connect to trade websocket
 */
export function connectWebSocket({
                                     onTrade,
                                     onConnect,
                                     onDisconnect,
                                     onError,
                                 }) {
    // Prevent duplicate connections
    if (stompClient?.connected) {
        return stompClient;
    }

    const socket = new SockJS("http://localhost:8080/ws");

    stompClient = new Client({
        webSocketFactory: () => socket,

        // Auto reconnect
        reconnectDelay: 5000,

        // Heartbeat
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,

        debug: () => {}, // disable noisy logs in production

        onConnect: () => {
            console.log("WebSocket connected");

            if (onConnect) {
                onConnect();
            }

            stompClient.subscribe("/topic/trades", (message) => {
                try {
                    const trade = JSON.parse(message.body);

                    if (onTrade) {
                        onTrade(trade);
                    }
                } catch (err) {
                    console.error("Trade parse error:", err);

                    if (onError) {
                        onError(err);
                    }
                }
            });
        },

        onDisconnect: () => {
            console.warn("WebSocket disconnected");

            if (onDisconnect) {
                onDisconnect();
            }
        },

        onStompError: (frame) => {
            console.error("Broker error:", frame);

            if (onError) {
                onError(frame);
            }
        },

        onWebSocketError: (event) => {
            console.error("WebSocket error:", event);

            if (onError) {
                onError(event);
            }
        },
    });

    stompClient.activate();

    return stompClient;
}

/**
 * Disconnect safely
 */
export function disconnectWebSocket() {
    if (stompClient) {
        stompClient.deactivate();
        stompClient = null;

        console.log("WebSocket disconnected safely");
    }
}