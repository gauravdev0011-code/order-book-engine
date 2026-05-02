export default function TradeFeed({ trades }) {
    return (
        <div className="bg-gray-900/80 p-4 rounded-xl border border-gray-700">
            <h2 className="text-lg font-medium mb-2">Trades</h2>

            <div className="text-sm max-h-72 overflow-y-auto space-y-1">
                {trades.length === 0 && (
                    <p className="text-gray-">Waiting for trades...</p>
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