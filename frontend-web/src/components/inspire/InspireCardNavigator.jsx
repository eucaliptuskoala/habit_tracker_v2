import { useState, useCallback } from "react";
import MoodIcon from "../MoodIcon";

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
