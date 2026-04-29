import { useEffect, useState } from "react";
import OrderForm from "../components/OrderForm";
import TradeFeed from "../components/TradeFeed";
import OrderBook from "../components/OrderBook";
import PriceChart from "../components/PriceChart";
import ThreeBackground from "../components/ThreeBackground";
import { connectWebSocket } from "../services/websocket";

export default function Dashboard() {
    const [trades, setTrades] = useState([]);

    useEffect(() => {
        connectWebSocket((trade) => {
            setTrades((prev) => [trade, ...prev.slice(0, 20)]);
        });
    }, []);

    return (
        <div className="min-h-screen bg-black text-white relative">
            <ThreeBackground />

            <div className="relative z-10 p-6 max-w-7xl mx-auto">
                <h1 className="text-3xl font-semibold mb-6 tracking-wide">
                    Trading Dashboard
                </h1>

                <div className="grid grid-cols-4 gap-5">
                    <OrderBook />
                    <TradeFeed trades={trades} />
                    <OrderForm />
                    <PriceChart />
                </div>
            </div>
        </div>
    );
}