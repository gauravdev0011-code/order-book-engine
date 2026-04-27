import { useState } from "react";
import axios from "axios";

export default function OrderForm() {
    const [price, setPrice] = useState("");
    const [quantity, setQuantity] = useState("");
    const [side, setSide] = useState("BUY");

    const submit = async () => {
        try {
            await axios.post("http://localhost:8080/order", {
                price: Number(price),
                quantity: Number(quantity),
                side,
            });

            setPrice("");
            setQuantity("");
        } catch (e) {
            console.error("Order failed", e);
        }
    };

    return (
        <div className="bg-black/60 p-4 rounded-xl border border-gray-700">
            <h2 className="text-xl font-bold mb-3">Place Order</h2>

            <input
                className="w-full mb-2 p-2 bg-gray-800 rounded"
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
            />

            <input
                className="w-full mb-2 p-2 bg-gray-800 rounded"
                placeholder="Quantity"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
            />

            <select
                className="w-full mb-2 p-2 bg-gray-800 rounded"
                value={side}
                onChange={(e) => setSide(e.target.value)}
            >
                <option value="BUY">BUY</option>
                <option value="SELL">SELL</option>
            </select>

            <button
                onClick={submit}
                className="w-full bg-blue-600 hover:bg-blue-700 p-2 rounded"
            >
                Submit
            </button>
        </div>
    );
}