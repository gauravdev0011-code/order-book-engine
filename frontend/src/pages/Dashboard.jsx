import ThreeBackground from "../components/ThreeBackground";
import OrderBook from "../components/OrderBook";
import TradeFeed from "../components/TradeFeed";
import OrderForm from "../components/OrderForm";
import PriceChart from "../components/PriceChart";

export default function Dashboard() {
    return (
        <div style={{ padding: "20px" }}>
            <ThreeBackground />

            <h1>Order Book Engine</h1>

            <div style={grid}>
                <OrderBook />
                <TradeFeed />
                <OrderForm />
                <PriceChart />
            </div>
        </div>
    );
}

const grid = {
    display: "grid",
    gridTemplateColumns: "repeat(4, 1fr)",
    gap: "15px",
};