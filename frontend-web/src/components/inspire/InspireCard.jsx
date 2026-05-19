function MoodIconSmall({ mood }) {
  const s = { width: 20, height: 20 };
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
  if (mood === "OKAY")
    return (
      <svg {...s} viewBox="0 0 20 20" fill="none">
        <circle cx="10" cy="10" r="8" fill="oklch(90% 0.05 100)" />
        <ellipse cx="7" cy="8" rx="1.5" ry="1.5" fill="oklch(67% 0.06 110)" opacity="0.35" />
        <ellipse cx="13" cy="8" rx="1.5" ry="1.5" fill="oklch(67% 0.06 110)" opacity="0.35" />
        <path d="M7 13c1.5 1 4.5 1 6 0" stroke="oklch(67% 0.06 110)" strokeWidth="1" fill="none" />
      </svg>
    );
  return (
    <svg {...s} viewBox="0 0 20 20" fill="none">
      <circle cx="10" cy="10" r="8" fill="oklch(85% 0.06 250)" />
      <ellipse cx="7" cy="8" rx="1.5" ry="1.5" fill="oklch(55% 0.08 250)" opacity="0.4" />
      <ellipse cx="13" cy="8" rx="1.5" ry="1.5" fill="oklch(55% 0.08 250)" opacity="0.4" />
      <path d="M7 13c1.5-1 4.5-1 6 0" stroke="oklch(55% 0.08 250)" strokeWidth="1" fill="none" />
    </svg>
  );
}

function InspireCard({ entry }) {
  const userName = entry.user?.name || "Anonymous";
  const initial = userName.charAt(0).toUpperCase();
  const categoryName = entry.habit?.categoryName;
  const date = entry.date ? new Date(entry.date + "T00:00:00").toLocaleDateString("en-US", { month: "short", day: "numeric", year: "numeric" }) : "";

  return (
    <div className="inspire-card">
      <div className="inspire-card-head">
        <div className="inspire-card-author">
          <div className="inspire-card-avatar">{initial}</div>
          <div>
            <div className="inspire-card-name">{userName}</div>
            <div className="inspire-card-meta" style={{ marginTop: "2px" }}>
              {categoryName && <span className="badge">{categoryName}</span>}
            </div>
          </div>
        </div>
        {entry.mood && <MoodIconSmall mood={entry.mood} />}
      </div>
      <div className="inspire-card-content">
        {entry.content}
      </div>
      <div className="inspire-card-foot">
        <span className="inspire-card-time">{date}</span>
        <button className="inspire-card-like">
          <svg viewBox="0 0 16 16" width="14" height="14">
            <path d="M8 3.5C6.5 1 3 1 3 4.5 3 7 8 11 8 11s5-4 5-6.5C13 1 9.5 1 8 3.5z" stroke="currentColor" strokeWidth="1.2" fill="none" />
          </svg>
        </button>
      </div>
    </div>
  );
}

export default InspireCard;
