import { useState } from "react";

export default function OrderForm() {
    const [price, setPrice] = useState("");
    const [quantity, setQuantity] = useState("");
    const [side, setSide] = useState("BUY");

    const submitOrder = async () => {
        try {
            await fetch("http://localhost:8080/order", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    price: Number(price),
                    quantity: Number(quantity),
                    side: side,
                }),
            });

            console.log("Order sent");
            setPrice("");
            setQuantity("");
        } catch (err) {
            console.error("Error:", err);
        }
    };

    return (
        <div style={card}>
            <h3>Place Order</h3>

            <select value={side} onChange={(e) => setSide(e.target.value)} style={input}>
                <option value="BUY">BUY</option>
                <option value="SELL">SELL</option>
            </select>

            <input
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                style={input}
            />

            <input
                placeholder="Quantity"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
                style={input}
            />

            <button onClick={submitOrder} style={button}>
                Submit
            </button>
        </div>
    );
}

const card = {
    background: "#111",
    padding: "15px",
    borderRadius: "10px",
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