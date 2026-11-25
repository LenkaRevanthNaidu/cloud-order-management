
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api.js";

function Dashboard() {
    const navigate = useNavigate();

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (!token) {
            navigate("/login");
            return;
        }

        const fetchProducts = async () => {
            try {
                const response = await api.get("/products");
                setProducts(response.data.content);
            } catch (err) {
                if (err.response?.status === 401 ||
                    err.response?.status === 403) {
                    localStorage.removeItem("token");
                    navigate("/login");
                } else {
                    setError("Unable to load products");
                }
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <div className="dashboard">
            <header className="dashboard-header">
                <h2>Cloud Order Management</h2>
                <button onClick={handleLogout}>Logout</button>
            </header>

            <main>
                <h2>Product Inventory</h2>

                {loading && <p>Loading products...</p>}
                {error && <p className="error">{error}</p>}

                {!loading && !error && (
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Quantity</th>
                        </tr>
                        </thead>

                        <tbody>
                        {products.map((product) => (
                            <tr key={product.id}>
                                <td>{product.id}</td>
                                <td>{product.name}</td>
                                <td>₹{product.price}</td>
                                <td>{product.quantity}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </main>
        </div>
    );
}

export default Dashboard;
