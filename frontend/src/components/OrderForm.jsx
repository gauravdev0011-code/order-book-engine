export default function OrderForm() {
    return (
        <div style={card}>
            <h3>Place Order</h3>

            <input placeholder="Price" style={input} />
            <input placeholder="Quantity" style={input} />

            <button style={btn}>Submit</button>
        </div>
    );
}

const card = {
    background: "#111",
    padding: "15px",
    borderRadius: "8px",
};

const input = {
    display: "block",
    width: "100%",
    marginBottom: "10px",
    padding: "8px",
};

const btn = {
    width: "100%",
    padding: "10px",
    background: "blue",
    color: "white",
};