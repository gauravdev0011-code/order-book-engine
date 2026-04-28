export default function Dashboard() {
    return (
        <div
            style={{
                backgroundColor: "black",
                color: "white",
                height: "100vh",
                padding: "20px",
                fontFamily: "Arial",
            }}
        >
            <h1>Order Book Engine</h1>

            <div style={{ display: "flex", gap: "20px", marginTop: "20px" }}>
                <div style={card}>
                    <h3>Order Book</h3>
                    <p>Live bids/asks</p>
                </div>

                <div style={card}>
                    <h3>Trade Feed</h3>
                    <p>Real-time trades</p>
                </div>

                <div style={card}>
                    <h3>Place Order</h3>
                    <input placeholder="Price" style={input} />
                    <input placeholder="Quantity" style={input} />
                    <button style={button}>Submit</button>
                </div>

                <div style={card}>
                    <h3>Price Chart</h3>
                    <p>Coming soon</p>
                </div>
            </div>
        </div>
    );
}

const card = {
    background: "#111",
    padding: "15px",
    borderRadius: "10px",
    width: "250px",
};

const input = {
    display: "block",
    width: "100%",
    marginBottom: "10px",
    padding: "8px",
};

const button = {
    width: "100%",
    padding: "10px",
    background: "blue",
    color: "white",
    border: "none",
};