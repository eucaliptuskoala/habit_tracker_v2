import { useState, useEffect, useRef } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import AuthHandler from "../../apis/AuthHandler";

const navItems = [
  {
    label: "Dashboard",
    path: "/dashboard",
    icon: (
      <svg viewBox="0 0 18 18">
        <rect x="2" y="2" width="6" height="6" rx="1.5" fill="currentColor" />
        <rect x="10" y="2" width="6" height="6" rx="1.5" fill="currentColor" />
        <rect x="2" y="10" width="6" height="6" rx="1.5" fill="currentColor" />
        <rect x="10" y="10" width="6" height="6" rx="1.5" fill="currentColor" />
      </svg>
    ),
  },
  {
    label: "Check-Ins",
    path: "/checkins",
    icon: (
      <svg viewBox="0 0 18 18">
        <path d="M3 9l4 4 8-8" stroke="currentColor" strokeWidth="1.5" fill="none" strokeLinecap="round" />
      </svg>
    ),
  },
  {
    label: "Progress",
    path: "/progress",
    icon: (
      <svg viewBox="0 0 18 18">
        <rect x="2" y="8" width="3" height="8" rx="1" fill="currentColor" />
        <rect x="7.5" y="4" width="3" height="12" rx="1" fill="currentColor" />
        <rect x="13" y="2" width="3" height="14" rx="1" fill="currentColor" />
      </svg>
    ),
  },
  {
    label: "Inspire",
    path: "/inspire",
    icon: (
      <svg viewBox="0 0 18 18">
        <circle cx="9" cy="9" r="7" stroke="currentColor" strokeWidth="1.5" fill="none" />
        <circle cx="9" cy="9" r="2" fill="currentColor" />
      </svg>
    ),
  },
];

function NavBar() {
  const [isOpen, setIsOpen] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const menuRef = useRef(null);
  const triggerRef = useRef(null);

  const token = AuthHandler.tokenExists();
  const userName = AuthHandler.getName() || "User";
  const avatarLetter = userName.charAt(0).toUpperCase();

  useEffect(() => {
    const handleClick = (e) => {
      if (
        menuRef.current &&
        triggerRef.current &&
        !menuRef.current.contains(e.target) &&
        !triggerRef.current.contains(e.target)
      ) {
        setIsOpen(false);
      }
    };
    document.addEventListener("click", handleClick);
    return () => document.removeEventListener("click", handleClick);
  }, []);

  const handleSignOut = () => {
    AuthHandler.clearToken();
    navigate("/");
  };

  const isPublic = location.pathname === "/" || location.pathname === "/sign-in" || location.pathname === "/sign-up";

  return (
    <nav className="nav-bar">
      <Link to={token ? "/dashboard" : "/"} className="nav-logo">
        <span className="nav-logo-sun" />
        Solen
      </Link>

      {isPublic ? (
        <div style={{ display: "flex", gap: "var(--space-md)", alignItems: "center" }}>
          <Link to="/sign-in" className="btn btn-ghost btn-sm">
            Sign in
          </Link>
          <Link to="/sign-up" className="btn btn-primary btn-sm">
            Get started
          </Link>
        </div>
      ) : (
        <div style={{ position: "relative" }}>
          <button
            ref={triggerRef}
            className="nav-dropdown-trigger"
            onClick={() => setIsOpen((p) => !p)}
          >
            <span className="nav-dropdown-avatar">{avatarLetter}</span>
            <span>{userName}</span>
            <svg
              className={`nav-dropdown-chevron${isOpen ? " open" : ""}`}
              viewBox="0 0 14 14"
            >
              <path d="M3 5l4 4 4-4" stroke="currentColor" strokeWidth="1.5" fill="none" />
            </svg>
          </button>

          <div ref={menuRef} className={`nav-dropdown-menu${isOpen ? " open" : ""}`}>
            <div className="nav-dropdown-section-label">Navigate</div>
            {navItems.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                className={`nav-dropdown-item${location.pathname === item.path ? " active" : ""}`}
                onClick={() => setIsOpen(false)}
              >
                {item.icon}
                {item.label}
              </Link>
            ))}
            <div className="nav-dropdown-divider" />
            <button className="nav-dropdown-item" onClick={handleSignOut}>
              Sign out
            </button>
          </div>
        </div>
      )}
    </nav>
  );
}

export default NavBar;
