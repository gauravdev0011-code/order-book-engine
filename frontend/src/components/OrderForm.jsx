import { useState } from "react";

export default function OrderForm() {
    const [price, setPrice] = useState("");
    const [qty, setQty] = useState("");

    const submit = async () => {
        await fetch("http://localhost:8080/order", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                price: Number(price),
                quantity: Number(qty),
                side: "BUY",
            }),
        });

        setPrice("");
        setQty("");
    };

    return (
        <div className="bg-black/70 p-4 rounded-xl border border-gray-700">
            <h2 className="text-lg font-semibold mb-2">Place Order</h2>

            <input
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                className="w-full mb-2 p-2 text-black"
            />

            <input
                placeholder="Quantity"
                value={qty}
                onChange={(e) => setQty(e.target.value)}
                className="w-full mb-2 p-2 text-black"
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