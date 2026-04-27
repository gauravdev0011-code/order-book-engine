import { useEffect, useState } from "react";
import { connectWebSocket } from "../services/websocket";

export default function TradeFeed() {
    const [trades, setTrades] = useState([]);

    useEffect(() => {
        const client = connectWebSocket((trade) => {
            setTrades((prev) => [trade, ...prev.slice(0, 20)]);
        });

        return () => client.deactivate();
    }, []);

    return (
        <div className="bg-black/60 p-4 rounded-xl border border-gray-700">
            <h2 className="text-xl font-bold mb-2">Trade Feed</h2>

            {trades.length === 0 && <p className="text-gray-400">Waiting for trades...</p>}

            {trades.map((t, i) => (
                <div key={i} className="flex justify-between text-sm py-1">
                    <span>{t.price}</span>
                    <span>{t.quantity}</span>
                    <span className={t.side === "BUY" ? "text-green-400" : "text-red-400"}>
            {t.side}
          </span>
                </div>
            ))}
        </div>
    );
}