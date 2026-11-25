
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api.js";

function Register() {
    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();
        setError("");

        try {
            await api.post("/auth/register", {
                name,
                email,
                password
            });

            navigate("/login");

        } catch (err) {
            setError(
                err.response?.data?.message || "Registration failed"
            );
        }
    };

    return (
        <div className="auth-container">
            <form className="auth-card" onSubmit={handleRegister}>
                <h2>Create Account</h2>
                <p>Register to manage your orders and inventory</p>

                {error && <div className="error">{error}</div>}

                <input
                    type="text"
                    placeholder="Full Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <input
                    type="password"
                    placeholder="Password (minimum 8 characters)"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    minLength={8}
                    required
                />

                <button type="submit">Register</button>

                <p>
                    Already have an account?{" "}
                    <Link to="/login">Login</Link>
                </p>
            </form>
        </div>
    );
}

export default Register;
