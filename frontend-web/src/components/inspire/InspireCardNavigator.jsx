import { useState, useCallback } from "react";

function MoodIcon({ mood, size = 24 }) {
  const s = { width: size, height: size };
  if (mood === "AWESOME")
    return (
      <svg {...s} viewBox="0 0 20 20" fill="none">
        <circle cx="10" cy="10" r="8" fill="oklch(82% 0.18 80)" />
        <ellipse cx="7" cy="8" rx="1.5" ry="1" fill="oklch(78% 0.18 75)" />
        <ellipse cx="13" cy="8" rx="1.5" ry="1" fill="oklch(78% 0.18 75)" />
        <path d="M7 13c1.5 1.5 4.5 1.5 6 0" stroke="oklch(78% 0.18 75)" strokeWidth="1" fill="none" />
      </svg>
    );
  if (mood === "GOOD")
    return (
      <svg {...s} viewBox="0 0 20 20" fill="none">
        <circle cx="10" cy="10" r="8" fill="oklch(88% 0.12 80)" />
        <ellipse cx="7" cy="8" rx="1.5" ry="1.5" fill="oklch(72% 0.12 80)" />
        <ellipse cx="13" cy="8" rx="1.5" ry="1.5" fill="oklch(72% 0.12 80)" />
        <path d="M7 13c1.5 1.5 4.5 1.5 6 0" stroke="oklch(72% 0.12 80)" strokeWidth="1" fill="none" />
      </svg>
    );
  return (
    <svg {...s} viewBox="0 0 20 20" fill="none">
      <circle cx="10" cy="10" r="8" fill="oklch(90% 0.05 100)" />
      <ellipse cx="7" cy="8" rx="1.5" ry="1.5" fill="oklch(67% 0.06 110)" opacity="0.35" />
      <ellipse cx="13" cy="8" rx="1.5" ry="1.5" fill="oklch(67% 0.06 110)" opacity="0.35" />
      <path d="M7 13c1.5 1 4.5 1 6 0" stroke="oklch(67% 0.06 110)" strokeWidth="1" fill="none" />
    </svg>
  );
}

function InspireCardNavigator({ entries }) {
  const [index, setIndex] = useState(0);

  const go = useCallback(
    (dir) => {
      setIndex((prev) => (prev + dir + entries.length) % entries.length);
    },
    [entries.length]
  );

  if (entries.length === 0) return null;

  const entry = entries[index];
  const userName = entry.user?.name || "Anonymous";
  const initial = userName.charAt(0).toUpperCase();
  const categoryName = entry.habit?.categoryName;
  const practiceName = entry.habit?.name;
  const date = entry.date
    ? new Date(entry.date + "T00:00:00").toLocaleDateString("en-US", {
        month: "short",
        day: "numeric",
        year: "numeric",
      })
    : "";

  return (
    <>
      <div className="inspire-nav-card" key={index}>
        <div className="inspire-card-head" style={{ marginBottom: "var(--space-lg)" }}>
          <div className="inspire-card-author">
            <div className="inspire-card-avatar">{initial}</div>
            <div>
              <div className="inspire-card-name">{userName}</div>
              <div className="inspire-card-meta" style={{ marginTop: "2px" }}>
                {categoryName && <span className="badge">{categoryName}</span>}
                {practiceName && <span className="badge" style={{ background: "none" }}>{practiceName}</span>}
              </div>
            </div>
          </div>
          {entry.mood && <MoodIcon mood={entry.mood} size={24} />}
        </div>
        <div className="inspire-card-content">{entry.content}</div>
        <div
          className="inspire-card-foot"
          style={{ justifyContent: "center", border: "none", paddingTop: 0, marginTop: "var(--space-lg)" }}
        >
          <span className="inspire-card-time">{date}</span>
        </div>
      </div>

      <div className="inspire-nav-controls">
        <button className="inspire-nav-btn" onClick={() => go(-1)}>
          <svg viewBox="0 0 16 16" width="16" height="16">
            <path d="M10 3L5 8l5 5" stroke="currentColor" strokeWidth="1.5" fill="none" strokeLinecap="round" />
          </svg>
        </button>
        <span className="inspire-nav-count">
          {index + 1} / {entries.length}
        </span>
        <button className="inspire-nav-btn" onClick={() => go(1)}>
          <svg viewBox="0 0 16 16" width="16" height="16">
            <path d="M6 3l5 5-5 5" stroke="currentColor" strokeWidth="1.5" fill="none" strokeLinecap="round" />
          </svg>
        </button>
      </div>
    </>
  );
}

export default InspireCardNavigator;
