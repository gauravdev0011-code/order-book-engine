import { useEffect, useState } from "react";
import OrderForm from "../components/OrderForm";
import TradeFeed from "../components/TradeFeed";
import { connectWebSocket } from "../services/websocket";

export default function Dashboard() {
    const [trades, setTrades] = useState([]);

    useEffect(() => {
        connectWebSocket((trade) => {
            setTrades((prev) => [trade, ...prev]);
        });
    }, []);

    return (
        <div style={{ background: "black", color: "white", padding: "20px" }}>
            <h1>Order Book Engine</h1>

            <div style={{ display: "flex", gap: "20px" }}>
                <OrderForm />
                <TradeFeed trades={trades} />
            </div>
        </div>
    );
}