import { useMemo } from "react";

export default function OrderBook({ bids = [], asks = [] }) {
    // Sort + limit depth (performance + UX)
    const sortedBids = useMemo(() => {
        return [...bids]
            .sort((a, b) => b.price - a.price)
            .slice(0, 10);
    }, [bids]);

    const sortedAsks = useMemo(() => {
        return [...asks]
            .sort((a, b) => a.price - b.price)
            .slice(0, 10);
    }, [asks]);

    return (
        <div className="bg-gray-900/80 backdrop-blur p-4 rounded-xl border border-gray-700 shadow-lg">
            <h2 className="text-lg font-semibold mb-3 tracking-wide">
                Order Book
            </h2>

            {/* HEADER */}
            <div className="grid grid-cols-2 text-xs text-gray-400 mb-2 px-1">
                <div>Bid (Buy)</div>
                <div className="text-right">Ask (Sell)</div>
            </div>

            <div className="grid grid-cols-2 gap-2 text-sm">
                {/* BIDS */}
                <div className="space-y-1">
                    {sortedBids.length === 0 && (
                        <p className="text-gray-500 text-xs">No bids</p>
                    )}

                    {sortedBids.map((b, i) => (
                        <div
                            key={i}
                            className="flex justify-between text-green-400 bg-green-900/20 px-2 py-1 rounded"
                        >
                            <span>{b.price}</span>
                            <span>{b.quantity}</span>
                        </div>
                    ))}
                </div>

                {/* ASKS */}
                <div className="space-y-1">
                    {sortedAsks.length === 0 && (
                        <p className="text-gray-500 text-xs text-right">No asks</p>
                    )}

                    {sortedAsks.map((a, i) => (
                        <div
                            key={i}
                            className="flex justify-between text-red-400 bg-red-900/20 px-2 py-1 rounded"
                        >
                            <span>{a.price}</span>
                            <span>{a.quantity}</span>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}