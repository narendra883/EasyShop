import React, { useEffect, useState } from "react";
import axios from "axios";

const Cart = ({ userId }) => {
    const [cart, setCart] = useState([]);
    const [error, setError] = useState("");
    const [total,setTotal]=useState(0);

    useEffect(() => {
        const fetchCartItems = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/cartitems/${userId}`);
                console.log(response.data);
                setCart(response.data); // Assuming response.data contains an array of cart items
            } catch (error) {
                console.error("Error fetching cart items:", error);
                setError("Failed to load cart items. Please try again.");
            }
        };

        fetchCartItems();
    }, [userId]); // Dependency array includes userId to refetch if it changes

    useEffect(() => {
        const newTotal = cart.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
        setTotal(newTotal);
    }, [cart]);

    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
            <h1 className="text-3xl font-semibold mb-6 text-indigo-600">Your Cart</h1>
            {error && (
                <p className="text-red-500 bg-red-100 p-4 rounded-md mb-4">
                    {error}
                </p>
            )}
            {cart.length > 0 ? (
                <ul className="space-y-6 w-full max-w-2xl">
                    {cart.map((item) => (
                        <li key={item.cart_id} className="p-6 bg-white rounded-lg shadow-lg">
                            <p className="text-lg font-medium text-gray-700">Product Name: <span className="font-normal">{item.product.name}</span></p>
                            <p className="text-gray-500">Description: {item.product.description}</p>
                            <p className="text-gray-500">Price: ${setTotal+item.product.price}</p>
                            <p className="text-gray-500">Quantity: {item.quantity}</p>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="text-gray-500 mt-4">Your cart is empty.</p>
            )}
            <div>
                <h1>Total price:${total.toFixed(2)}</h1>
            </div>
        </div>
    );
};

export default Cart;
