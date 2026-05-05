import { useState } from "react";

export default function OrderForm() {
    const [price, setPrice] = useState("");
    const [qty, setQty] = useState("");
    const [side, setSide] = useState("BUY");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const validate = () => {
        const p = Number(price);
        const q = Number(qty);

        if (!p || p <= 0) return "Invalid price";
        if (!q || q <= 0) return "Invalid quantity";

        return null;
    };

    const submit = async () => {
        setError("");
        setSuccess("");

        const validationError = validate();
        if (validationError) {
            setError(validationError);
            return;
        }

        setLoading(true);

        try {
            const res = await fetch("http://localhost:8080/order", {
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

            if (!res.ok) {
                throw new Error("Order failed");
            }

            setSuccess("Order placed successfully");
            setPrice("");
            setQty("");
        } catch (err) {
            setError(err.message || "Something went wrong");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="bg-gray-900/80 backdrop-blur p-4 rounded-xl border border-gray-700 shadow-lg">
            <h2 className="text-lg font-semibold mb-3 tracking-wide">
                Place Order
            </h2>

            <select
                className="w-full mb-2 p-2 bg-black border border-gray-700 rounded focus:outline-none"
                value={side}
                onChange={(e) => setSide(e.target.value)}
            >
                <option value="BUY">BUY</option>
                <option value="SELL">SELL</option>
            </select>

            <input
                type="number"
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                className="w-full mb-2 p-2 bg-black border border-gray-700 rounded focus:outline-none"
            />

            <input
                type="number"
                placeholder="Quantity"
                value={qty}
                onChange={(e) => setQty(e.target.value)}
                className="w-full mb-3 p-2 bg-black border border-gray-700 rounded focus:outline-none"
            />

            <button
                onClick={submit}
                disabled={loading}
                className={`w-full p-2 rounded transition ${
                    loading
                        ? "bg-gray-600 cursor-not-allowed"
                        : "bg-blue-600 hover:bg-blue-700"
                }`}
            >
                {loading ? "Processing..." : "Submit"}
            </button>

            {/* Feedback */}
            {error && (
                <p className="text-red-400 mt-2 text-sm">{error}</p>
            )}
            {success && (
                <p className="text-green-400 mt-2 text-sm">{success}</p>
            )}
        </div>
    );
}