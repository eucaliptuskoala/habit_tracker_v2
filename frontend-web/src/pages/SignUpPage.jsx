import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import UserAPI from "../apis/UserAPI";
import AuthAPI from "../apis/AuthAPI";
import AuthHandler from "../apis/AuthHandler";
import HabitAPI from "../apis/HabitAPI";
import CategoryTreeBrowser from "../components/landing/CategoryTreeBrowser";

function SignUpPage() {
  const [step, setStep] = useState(1);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleStep1 = (e) => {
    e.preventDefault();
    setError("");

    if (!name || !email || password.length < 8) {
      setError("Please fill in all fields with a valid password (8+ characters).");
      return;
    }

    UserAPI.createUser({ name, email, password, isAdmin: false })
      .then((user) => {
        return AuthAPI.signIn({ email, password }).then((data) => {
          AuthHandler.saveToken(data.token);
          return user;
        });
      })
      .then(() => {
        setStep(2);
      })
      .catch((err) => {
        setError(err?.response?.data || "Something went wrong. Please try again.");
      });
  };

  const handleFinish = (selected) => {
    if (selected.length > 0) {
      Promise.all(
        selected.map(({ id: categoryId, name }) =>
          HabitAPI.createHabit({ name, description: "", userId: AuthHandler.getUserId(), categoryId })
        )
      ).catch(() => {}).finally(() => navigate("/dashboard"));
    } else {
      navigate("/dashboard");
    }
  };

  return (
    <main className="signup-container">
      <div className={`onboarding-step${step === 1 ? " active" : ""}`} id="step1">
        <div className="signup-header">
          <h1 className="display-lg">Join Solen</h1>
          <p>Create your account to begin your practice.</p>
        </div>

        <form className="signup-form" onSubmit={handleStep1}>
          {error && (
            <div style={{ background: "oklch(55% 0.08 250 / 0.1)", color: "var(--mood-awful)", padding: "10px 14px", borderRadius: "var(--radius)", marginBottom: "var(--space-md)", fontSize: "0.85rem" }}>
              {error}
            </div>
          )}

          <div className="field">
            <label htmlFor="name">Name</label>
            <input type="text" id="name" className="input" placeholder="Your name" required value={name} onChange={(e) => setName(e.target.value)} />
          </div>
          <div className="field">
            <label htmlFor="email">Email</label>
            <input type="email" id="email" className="input" placeholder="you@example.com" required value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div className="field">
            <label htmlFor="password">Password</label>
            <input type="password" id="password" className="input" placeholder="Choose a password" required minLength={8} value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <button type="submit" className="btn btn-primary">Continue</button>
        </form>

        <div className="signup-footer">
          By continuing, you agree to Solen's terms and privacy policy.
        </div>
      </div>

      <div className={`onboarding-step${step === 2 ? " active" : ""}`} id="step2">
        <div className="signup-header onboarding-header">
          <span className="label" style={{ display: "block", marginBottom: "var(--space-sm)" }}>Step 2 of 2</span>
          <h2 className="display-lg">Pick your practices</h2>
          <p>Browse categories and select the practices you'd like to track. You can always change these later.</p>
        </div>

        <CategoryTreeBrowser onSelect={handleFinish} />
      </div>
    </main>
  );
}

export default SignUpPage;
