import React, { useState } from 'react';

function AddToCartButton({ userId, productId, quantity = 1 }) {
    const [isAdding, setIsAdding] = useState(false);
    const [message, setMessage] = useState("");

    const handleAddToCart = async () => {
        setIsAdding(true);
        setMessage(""); // Clear any previous message
        try {
            const response = await fetch(`http://localhost:8080/api/cartitems/${userId}/${productId}?quantity=${quantity}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ quantity })
            });
    
            if (!response.ok) {
                throw new Error("Failed to add to cart");
            }
    
            const data = await response.text();  // Parse as text
            setMessage(data);  // Display the message directly
            console.log("Added to cart:", data);
        } catch (error) {
            console.error("Error adding to cart:", error);
            setMessage("Error adding to cart. Please try again.");
        } finally {
            setIsAdding(false);
        }
    };
    

    return (
        <div className="flex flex-col items-center mt-4">
            <button
                onClick={handleAddToCart}
                disabled={isAdding}
                className={`px-4 py-2 font-semibold rounded-md ${
                    isAdding ? "bg-gray-400 cursor-not-allowed" : "bg-blue-600 hover:bg-blue-700 text-white"
                } transition duration-300 ease-in-out`}
            >
                {isAdding ? "Adding..." : "Add to Cart"}
            </button>
            {message && (
                <p className={`mt-2 text-sm ${message.includes("Error") ? "text-red-500" : "text-green-500"}`}>
                    {message}
                </p>
            )}
        </div>
    );
}

export default AddToCartButton;
