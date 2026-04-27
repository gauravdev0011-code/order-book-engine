import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export function connectWebSocket(onTrade) {
    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,

        onConnect: () => {
            console.log("Connected to WebSocket");

            client.subscribe("/topic/trades", (message) => {
                const trade = JSON.parse(message.body);
                onTrade(trade);
            });
        },

        onStompError: (frame) => {
            console.error("STOMP error:", frame);
        },
    });

    client.activate();
}