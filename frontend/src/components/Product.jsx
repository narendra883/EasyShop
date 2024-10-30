import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddToCartButton from './AddToCartButton';

const Products = ({ userId }) => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/products");
                setProducts(response.data);
            } catch (error) {
                console.error("Error loading products:", error);
            }
        };

        fetchProducts();
    }, []);

    return (
        <div className="p-8 bg-gray-100 min-h-screen">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-8">Our Products</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {products.map((product) => (
                    <div key={product.id} className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-200">
                        <h3 className="text-xl font-semibold text-gray-700">{product.name}</h3>
                        <p className="text-gray-600 mt-2 mb-4">{product.description}</p>
                        <p className="text-lg font-bold text-indigo-600 mb-4">Price: ${product.price}</p>
                        <AddToCartButton productId={product.id} userId={userId} />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Products;
