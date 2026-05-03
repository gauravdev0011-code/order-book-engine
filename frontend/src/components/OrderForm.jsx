import { useState } from "react";

export default function OrderForm() {
    const [price, setPrice] = useState("");
    const [qty, setQty] = useState("");
    const [side, setSide] = useState("BUY");

    const submit = async () => {
        await fetch("http://localhost:8080/order", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                price: Number(price),
                quantity: Number(qty),
                side,
            }),
        });

        setPrice("");
        setQty("");
    };

    return (
        <div className="bg-gray-900/80 p-4 rounded-xl border border-gray-700">
            <h2 className="text-lg font-medium mb-3">Place Order</h2>

            <select
                className="w-full mb-2 p-2 bg-black border border-gray-700"
                value={side}
                onChange={(e) => setSide(e.target.value)}
            >
                <option value="BUY">BUY</option>
                <option value="SELL">SELL</option>
            </select>

            <input
                className="w-full mb-2 p-2 bg-black border border-gray-700"
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
            />

            <input
                className="w-full mb-3 p-2 bg-black border border-gray-700"
                placeholder="Quantity"
                value={qty}
                onChange={(e) => setQty(e.target.value)}
            />

            <button
                onClick={submit}
                className="w-full bg-blue-600 p-2 rounded"
            >
                Submit
            </button>
        </div>
    );
}