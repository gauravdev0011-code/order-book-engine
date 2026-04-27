import { useState } from "react";
import axios from "axios";

export default function OrderForm() {
    const [price, setPrice] = useState("");
    const [quantity, setQuantity] = useState("");

    const submit = async () => {
        await axios.post("http://localhost:8080/order", {
            price: Number(price),
            quantity: Number(quantity),
            side: "BUY",
        });
    };

    return (
        <div className="bg-black/60 p-4 rounded-xl">
            <h2 className="text-xl mb-2">Place Order</h2>

            <input
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                className="w-full mb-2 p-2 bg-gray-800"
            />

            <input
                placeholder="Quantity"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
                className="w-full mb-2 p-2 bg-gray-800"
            />

            <button
                onClick={submit}
                className="w-full bg-blue-600 p-2"
            >
                Submit
            </button>
        </div>
    );
}