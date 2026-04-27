import { useEffect, useState } from "react";
import { connectWebSocket } from "../services/websocket";

export default function TradeFeed() {
    const [trades, setTrades] = useState([]);

    useEffect(() => {
        const client = connectWebSocket((trade) => {
            setTrades((prev) => [trade, ...prev.slice(0, 10)]);
        });

        return () => client.deactivate();
    }, []);

    return (
        <div className="bg-black/60 p-4 rounded-xl">
            <h2 className="text-xl font-bold mb-2">Trade Feed</h2>

            {trades.map((t, i) => (
                <div key={i} className="flex justify-between text-sm">
                    <span>{t.price}</span>
                    <span>{t.quantity}</span>
                </div>
            ))}
        </div>
    );
}