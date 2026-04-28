import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export function connectWebSocket(onTrade) {
    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
        webSocketFactory: () => socket,

        onConnect: () => {
            console.log("Connected");

            client.subscribe("/topic/trades", (msg) => {
                const trade = JSON.parse(msg.body);
                onTrade(trade);
            });
        },
    });

    client.activate();
}