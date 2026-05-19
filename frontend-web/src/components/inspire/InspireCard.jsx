import MoodIcon from "../MoodIcon";

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
        {entry.mood && <span className="inspire-card-mood"><MoodIcon mood={entry.mood} size={20} /></span>}
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
