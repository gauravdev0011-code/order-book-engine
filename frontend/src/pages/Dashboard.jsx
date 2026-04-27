import { useEffect, useState } from "react";
import ThreeBackground from "../components/ThreeBackground";
import OrderBook from "../components/OrderBook";
import TradeFeed from "../components/TradeFeed";
import OrderForm from "../components/OrderForm";
import PriceChart from "../components/PriceChart";
import { connectWebSocket } from "../services/websocket";

export default function Dashboard() {
    const [trades, setTrades] = useState([]);

    useEffect(() => {
        connectWebSocket((trade) => {
            setTrades((prev) => [trade, ...prev.slice(0, 20)]);
        });
    }, []);

    return (
        <div className="min-h-screen text-white p-6 bg-black">
            <ThreeBackground />

            <h1 className="text-3xl font-bold mb-6">
                Order Book Engine
            </h1>

            <div className="grid grid-cols-4 gap-4">
                <OrderBook />
                <TradeFeed trades={trades} />
                <OrderForm />
                <PriceChart />
            </div>
        </div>
    );
}