import React, { useState, useEffect} from "react";
import {Link, useNavigate} from "react-router-dom";
import AuthAPI from "../apis/AuthAPI";
import AuthHandler from "../apis/AuthHandler";

function SignInPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        if(message || errorMessage){
          const timer = setTimeout(() => {
            setMessage("");
            setErrorMessage("");
          }, 3000);
          return () => clearTimeout(timer);
        }
    }, [message, errorMessage]);

    const handleSignIn = (e) => {
        e.preventDefault();

        const request = {
            email: email,
            password: password
        }
         
        AuthAPI.signIn(request).then(data =>{
            AuthHandler.saveToken(data.token);
            setMessage("Sign-in successful!")
            navigate("/dashboard")
        }).catch(error =>{
            if(error){
                setErrorMessage(error.response.data)
            }else{
                setErrorMessage("Unknown error occured!")
            }
        })
    };

    return (
    <div className="min-h-screen flex items-center justify-center px-6 py-16">
        <div className="w-full max-w-lg bg-[var(--color-6)] rounded-2xl shadow-xl p-10 border border-[var(--color-5)]">

            <h1 className="text-3xl font-semibold text-center text-black mb-2">
                Welcome back
            </h1>
            <p className="text-sm text-gray-600 text-center mb-8">
                Sign in to continue building better habits
            </p>

            {errorMessage && (
            <div className="bg-red-100 text-red-700 p-3 rounded-lg mb-6 relative text-sm">
                {errorMessage}
                <button
                onClick={() => setErrorMessage("")}
                className="absolute top-2 right-3 font-bold"
                >
                ×
                </button>
            </div>
            )}

            {message && (
            <div className="bg-green-100 text-green-700 p-3 rounded-lg mb-6 relative text-sm">
                {message}
                <button
                onClick={() => setMessage("")}
                className="absolute top-2 right-3 font-bold"
                >
                ×
                </button>
            </div>
            )}

            <form onSubmit={handleSignIn} className="space-y-6">
                <div>
                    <label className="block text-sm text-gray-700 mb-1">
                    Email
                    </label>
                    <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    className="w-full px-4 py-3 rounded-xl border border-[var(--color-8)] bg-white
                                focus:outline-none focus:ring-2 focus:ring-[var(--color-9)]"
                    />
                </div>

                <div>
                    <label className="block text-sm text-gray-700 mb-1">
                    Password
                    </label>
                    <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className="w-full px-4 py-3 rounded-xl border border-[var(--color-8)] bg-white
                                focus:outline-none focus:ring-2 focus:ring-[var(--color-9)]"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full py-3 rounded-xl bg-[var(--color-9)] text-black text-lg font-medium
                            hover:bg-[var(--color-8)] transition-all hover:shadow-md"
                >
                    Sign In
                </button>
            </form>

            <div className="mt-10 text-center text-sm text-gray-600">
                <p>Don’t have an account yet?</p>
                <Link
                    to="/sign-up"
                    className="inline-block mt-1 font-medium hover:text-black"
                >
                    Sign up ✦
                </Link>
            </div>
        </div>
    </div>
  );
}

export default SignInPage;
