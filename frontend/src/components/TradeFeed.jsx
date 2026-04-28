export default function TradeFeed({ trades }) {
    return (
        <div style={card}>
            <h3>Trade Feed</h3>

            {trades.length === 0 && <p>No trades yet</p>}

            {trades.map((t, i) => (
                <div key={i}>
                    {t.price} | {t.quantity}
                </div>
            ))}
        </div>
    );
}

const card = {
    background: "#111",
    padding: "15px",
    borderRadius: "10px",
};