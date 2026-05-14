import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import UserAPI from "../apis/UserAPI";
import HabitTemplateAPI from "../apis/HabitTemplateAPI";
import HabitAPI from "../apis/HabitAPI";
import HabitStrategySelector from "../components/habit/HabitStrategySelector";
import AuthAPI from "../apis/AuthAPI";
import AuthHandler from "../apis/AuthHandler";

function SignUpPage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isAdmin, setIsAdmin] = useState(false);

  const [userCreated, setUserCreated] = useState(false);
  const [userId, setUserId] = useState(null);

  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const [habitTemplates, setHabitTemplates] = useState([]);
  const [popularHabitTemplates, setPopularHabitTemplates] = useState([]);

  const [creatingHabit, setCreatingHabit] = useState(false);
  const [createdHabit, setCreatedHabit] = useState(null);

  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  useEffect(() => {

    if (!isAuthenticated) return;

    HabitTemplateAPI.getAllHabitTemplates()
      .then(data => {
        setHabitTemplates(data);
      })

    HabitTemplateAPI.getPopularHabitTemplates()
      .then(data => {
        setPopularHabitTemplates(data);
      })
  }, [isAuthenticated]);

  useEffect(() => {
    if(message || errorMessage){
      const timer = setTimeout(() => {
        setMessage("");
        setErrorMessage("");
      }, 3000);
        return () => clearTimeout(timer);
    }
  }, [message, errorMessage]);

  const finishOnboarding = () => {
    navigate("/dashboard");
  };

  const handleCreateHabitFromSelector = async (req) => {
      setCreatingHabit(true);

      const habit = {
        name: req.name,
        description: req.description,
        userId: userId,
        templateId: req.templateId ?? null
      };

      const created = await HabitAPI.createHabit(habit);
      setCreatedHabit(created);
      setCreatingHabit(false);
  };


  const handleSignIn = (request, redirect = true) => {
    AuthAPI.signIn(request)
      .then(data => {
        AuthHandler.saveToken(data.token);
        setIsAuthenticated(true);
        setMessage("Sign-in successful!");

        if (redirect) {
          navigate("/dashboard");
        }
      })
      .catch(error => {
        if (error?.response) {
          setErrorMessage(error.response.data);
        } else {
          setErrorMessage("Unknown error occurred!");
        }
      });
  };


  const handleSubmit = (e) => {
      e.preventDefault();

      const user = {
        name: name,
        email: email,
        password: password,
        isAdmin: isAdmin
      };
      
      UserAPI.createUser(user)
          .then(data => {
              console.log("User created:", data);
              const user = data

              const signInRequest = {
                email: user.email,
                password: password
              }

              handleSignIn(signInRequest, false);

              setName("");
              setEmail("");
              setPassword("");
              setIsAdmin(false);
              setUserId(data.id);
              setUserCreated(true);
          }).catch(error => {
            if(error){
              setErrorMessage(error.response.data)
            }else{
                setErrorMessage("Unknown error occured!")
            }
          });
  }
if (!userCreated) {
  return (
    <div className="min-h-screen flex items-center justify-center px-6 py-16">
      <div className="w-full max-w-lg bg-[var(--color-6)] rounded-2xl shadow-xl p-10 border border-[var(--color-5)]">

        <h1 className="text-3xl font-semibold text-center text-black mb-2">
          Create your account
        </h1>
        <p className="text-sm text-gray-600 text-center mb-8">
          Start building habits that stick
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

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-sm text-gray-700 mb-1">
              Name
            </label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              className="w-full px-4 py-3 rounded-xl border border-[var(--color-8)] bg-white
                         focus:outline-none focus:ring-2 focus:ring-[var(--color-9)]"
            />
          </div>

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
            Sign Up
          </button>
        </form>

        <div className="mt-10 text-center text-sm text-gray-600">
          <p>Already have an account?</p>
          <Link
            to="/sign-in"
            className="inline-block mt-1 font-medium hover:text-black"
          >
            Sign in ✦
          </Link>
        </div>
      </div>
    </div>
  );
}

  return (
    <div className="min-h-screen flex flex-col items-center justify-start px-4 py-12 gap-6">
      <div className="w-full max-w-2xl bg-[var(--color-6)] rounded-2xl shadow-lg p-6 border border-[var(--color-5)]">
        <h2 className="text-2xl font-semibold mb-4">Choose your first Habit!</h2>
        <p className="mb-4 text-sm text-gray-600">Create one from existing templates or create a custom one. You can skip and do it later.</p>

        <HabitStrategySelector
          habitTemplates={habitTemplates}
          popularHabitTemplates={popularHabitTemplates}
          onSubmit={handleCreateHabitFromSelector}
        />
          <div className="flex gap-3">
            <button
              className="px-4 py-2 rounded-lg bg-white border"
              onClick={() => navigate("/dashboard")}
            >
              Skip
            </button>
            <button
              className="px-4 py-2 rounded-lg bg-[var(--color-9)]"
              onClick={finishOnboarding}
            >
              Finish
            </button>
          </div>
          {errorMessage && (
            <div className="bg-red-100 text-red-700 p-2 rounded mb-4 relative">
            <span>{errorMessage}</span>
            <button onClick={() => setErrorMessage("")} className="font-bold px-2 absolute top-3 right-3">×</button>
            </div>
          )}
          {message && (
            <div className="bg-green-100 text-green-700 p-2 rounded mb-4">
            <span>{message}</span>
            <button onClick={() => setMessage("")} className="font-bold px-2 absolute top-3 right-3">×</button>
            </div>
          )}
        </div>
      </div>
  )
}

export default SignUpPage;