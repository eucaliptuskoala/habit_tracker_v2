import { Link, useLocation } from "react-router-dom";

function Navbar({ isOpen, onClose, token }) {
  const location = useLocation();

  const links = [
    { name: "Dashboard", path: "/dashboard" },
    { name: "Notes", path: "/notes" },
    { name: "For You Page", path: "/fyp" },
    { name: "Progress", path: "/progress" },
  ];

  if(token){
    return (
      <div
        className={`absolute top-full left-0 w-full bg-[var(--color-8)] border-t border-[#d8c6c1] shadow-sm overflow-hidden transition-all duration-500 ease-[cubic-bezier(0.4,0,0.2,1)] ${
          isOpen ? "max-h-60 opacity-100" : "max-h-0 opacity-0"
        }`}
      >
        <ul className="flex flex-col items-center py-4 space-y-2">
          {links.map((link) => (
            <li key={link.path}>
              <Link
                to={link.path}
                onClick={onClose}
                className={`text-lg tracking-wide transition-colors ${
                  location.pathname === link.path
                    ? "text-black font-bold"
                    : "text-gray-700 hover:text-black"
                }`}
              >
                {link.name}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    );
  }
  return (
    <div
      className={`absolute top-full left-0 w-full bg-[var(--color-5)] border-t border-[#d8c6c1] shadow-sm overflow-hidden transition-all duration-500 ease-[cubic-bezier(0.4,0,0.2,1)] ${
        isOpen ? "max-h-60 opacity-100" : "max-h-0 opacity-0"
      }`}
    >
    </div>
  );
}

export default Navbar;
