import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    CartesianGrid,
} from "recharts";
import { useMemo } from "react";

export default function PriceChart({ trades = [] }) {
    // Convert trades into chart-friendly format
    const chartData = useMemo(() => {
        return trades
            .slice()
            .reverse()
            .map((trade, index) => ({
                index,
                price: trade.price,
            }));
    }, [trades]);

    return (
        <div className="bg-gray-900/80 backdrop-blur p-4 rounded-xl border border-gray-700 shadow-lg">
            <div className="flex items-center justify-between mb-3">
                <h2 className="text-lg font-semibold tracking-wide">
                    Price Chart
                </h2>

                <span className="text-xs text-gray-400">
          Real-time
        </span>
            </div>

            {chartData.length === 0 ? (
                <div className="h-64 flex items-center justify-center text-gray-500 text-sm">
                    Waiting for market data...
                </div>
            ) : (
                <div className="h-64">
                    <ResponsiveContainer width="100%" height="100%">
                        <LineChart data={chartData}>
                            <CartesianGrid
                                strokeDasharray="3 3"
                                stroke="#1f2937"
                            />

                            <XAxis
                                dataKey="index"
                                tick={{ fill: "#9ca3af", fontSize: 10 }}
                            />

                            <YAxis
                                tick={{ fill: "#9ca3af", fontSize: 10 }}
                                domain={["auto", "auto"]}
                            />

                            <Tooltip
                                contentStyle={{
                                    backgroundColor: "#111827",
                                    border: "1px solid #374151",
                                    borderRadius: "8px",
                                }}
                            />

                            <Line
                                type="monotone"
                                dataKey="price"
                                stroke="#00eaff"
                                strokeWidth={2}
                                dot={false}
                                isAnimationActive={true}
                                animationDuration={400}
                            />
                        </LineChart>
                    </ResponsiveContainer>
                </div>
            )}
        </div>
    );
}