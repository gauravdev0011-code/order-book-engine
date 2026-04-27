import ThreeBackground from "../components/ThreeBackground";
import OrderBook from "../components/OrderBook";
import TradeFeed from "../components/TradeFeed";
import OrderForm from "../components/OrderForm";
import PriceChart from "../components/PriceChart";

export default function Dashboard() {
    return (
        <div className="min-h-screen text-white p-6">
            <ThreeBackground />

            <h1 className="text-4xl font-bold mb-6">Order Book Engine</h1>

            <div className="grid grid-cols-4 gap-4">
                <OrderBook />
                <TradeFeed />
                <OrderForm />
                <PriceChart />
            </div>
        </div>
    );
}