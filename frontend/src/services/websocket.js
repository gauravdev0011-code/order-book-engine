export function connectWebSocket(onMessage) {
    const socket = new WebSocket("ws://localhost:8080/ws");

    socket.onopen = () => console.log("WS connected");

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        onMessage(data);
    };

    socket.onerror = (err) => console.error(err);

    return socket;
}