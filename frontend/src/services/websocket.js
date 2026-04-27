import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export function connectWebSocket(onTrade) {
    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,

        onConnect: () => {
            console.log("Connected");

            client.subscribe("/topic/trades", (msg) => {
                onTrade(JSON.parse(msg.body));
            });
        },
    });

    client.activate();
    return client;
}