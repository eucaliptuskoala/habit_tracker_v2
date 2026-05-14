import React from "react";
import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import AuthHandler from "../../apis/AuthHandler";

function Header() {
  const [isNavbarOpen, setIsNavbarOpen] = useState(false);
  const location = useLocation();

  const token = AuthHandler.tokenExists();

  const navigate = useNavigate();

  const isActive = (path) => location.pathname === path;

  const handleLogOut = () =>{
    AuthHandler.clearToken();
    navigate("/")
  }

  const getPageTitle = () => {
  switch(location.pathname) {
    case "/":
      return "Home";
    case "/dashboard":
      return "Dashboard";
    case "/notes":
      return "Notes";
    case "/sign-up":
      return "Sign Up";
    case "/sign-in":
      return "Sign In";
    case "/fyp":
      return "For You Page";
    case "/progress":
      return "Progress";
    default:
      return "";
  }
};

  return (
    <header className="header shadow-sm border-b border-[#d8c6c1] relative z-20">
      <nav
        aria-label="Global"
        className="mx-auto flex max-w-7xl items-stretch justify-between"
      >
        <div className="flex items-center px-8 py-4 bg-[var(--color-2)]">
          <Link to="/" className="-m-1 p-1">
            <h1 className="text-xl text-black">HB</h1>
          </Link>
        </div>

        <div
          className="flex-1 flex items-center justify-center bg-[var(--color-1)] px-8 py-4 cursor-pointer select-none transition-colors hover:bg-[var(--color-4)]"
          onClick={() => setIsNavbarOpen((prev) => !prev)}
        >
          <span
            className={`text-lg tracking-wide}`}
          >
            {getPageTitle()}
          </span>
        </div>

        {token ? (
          <div className="flex items-center gap-2 px-8 py-4 bg-[var(--color-2)]">
            <button onClick={handleLogOut} className="text-base text-black/70">
              Log out
            </button>
          </div>
        ):(
        <div className="flex items-center gap-2 px-8 py-4 bg-[var(--color-2)]">
          <Link
            to="sign-in"
            className={`text-base ${
              isActive("/sign-in") ? "text-black font-bold" : "text-black/70"
            }`}
          >
            Sign-in
          </Link>
        </div>
        )}
      </nav>

      <Navbar isOpen={isNavbarOpen} onClose={() => setIsNavbarOpen(false)} token={token} />
    </header>
  );
}

export default Header;