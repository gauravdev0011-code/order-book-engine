export default function TradeFeed({ trades = [] }) {
    return (
        <div className="bg-black/70 p-4 rounded-xl border border-gray-700">
            <h2 className="text-lg font-semibold mb-2">Trade Feed</h2>

            <div className="text-sm space-y-1 max-h-64 overflow-y-auto">
                {trades.length === 0 && (
                    <p className="text-gray-400">No trades yet</p>
                )}

                {trades.map((t, i) => (
                    <div key={i} className="flex justify-between">
                        <span>{t.price}</span>
                        <span>{t.quantity}</span>
                    </div>
                ))}
            </div>
        </div>
    );
}