import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthAPI from "../apis/AuthAPI";
import AuthHandler from "../apis/AuthHandler";

function SignInPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSignIn = (e) => {
    e.preventDefault();
    setError("");

    AuthAPI.signIn({ email, password })
      .then((data) => {
        AuthHandler.saveToken(data.token);
        navigate("/dashboard");
      })
      .catch((err) => {
        setError(err?.response?.data || "Something went wrong. Please try again.");
      });
  };

  return (
    <main className="auth-container animate-in">
      <div className="auth-header">
        <div className="auth-sun" />
        <h1 className="display-lg">Welcome back</h1>
        <p>Sign in to continue your practice.</p>
      </div>

      <form className="auth-form" onSubmit={handleSignIn}>
        {error && (
          <div style={{ background: "oklch(55% 0.08 250 / 0.1)", color: "var(--mood-awful)", padding: "10px 14px", borderRadius: "var(--radius)", marginBottom: "var(--space-md)", fontSize: "0.85rem" }}>
            {error}
          </div>
        )}

        <div className="field">
          <label htmlFor="email">Email</label>
          <input type="email" id="email" className="input" placeholder="you@example.com" required value={email} onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div className="field">
          <label htmlFor="password">Password</label>
          <input type="password" id="password" className="input" placeholder="Your password" required value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <button type="submit" className="btn btn-primary">Sign in</button>
      </form>

      <div className="auth-footer">
        Don't have an account? <Link to="/sign-up">Create one</Link>
      </div>
    </main>
  );
}

export default SignInPage;
