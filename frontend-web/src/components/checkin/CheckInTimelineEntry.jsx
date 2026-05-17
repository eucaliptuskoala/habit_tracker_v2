function MoodIconSmall({ mood }) {
  if (mood === "AWFUL")
    return (
      <svg className="timeline-mood-icon" viewBox="0 0 24 24" fill="none">
        <circle cx="12" cy="12" r="10" fill="oklch(85% 0.06 250)" />
        <path d="M4 4l3 3m-3 0l3-3" stroke="oklch(55% 0.08 250)" strokeWidth="1.2" strokeLinecap="round" />
        <ellipse cx="9" cy="10" rx="1.5" ry="2" fill="oklch(55% 0.08 250)" opacity="0.4" />
        <ellipse cx="15" cy="10" rx="1.5" ry="2" fill="oklch(55% 0.08 250)" opacity="0.4" />
        <path d="M7 16c2-1 6-1 8 0" stroke="oklch(55% 0.08 250)" strokeWidth="1" fill="none" />
      </svg>
    );
  if (mood === "BAD")
    return (
      <svg className="timeline-mood-icon" viewBox="0 0 24 24" fill="none">
        <circle cx="12" cy="12" r="10" fill="oklch(88% 0.05 230)" />
        <ellipse cx="9" cy="10" rx="1.5" ry="2" fill="oklch(62% 0.07 220)" opacity="0.3" />
        <ellipse cx="15" cy="10" rx="1.5" ry="2" fill="oklch(62% 0.07 220)" opacity="0.3" />
        <path d="M8 16c2-1 6-1 8 0" stroke="oklch(62% 0.07 220)" strokeWidth="1" fill="none" />
      </svg>
    );
  if (mood === "OKAY")
    return (
      <svg className="timeline-mood-icon" viewBox="0 0 24 24" fill="none">
        <circle cx="12" cy="12" r="10" fill="oklch(90% 0.05 100)" />
        <ellipse cx="8" cy="10" rx="1.5" ry="2" fill="oklch(67% 0.06 110)" opacity="0.35" />
        <ellipse cx="16" cy="10" rx="1.5" ry="2" fill="oklch(67% 0.06 110)" opacity="0.35" />
        <path d="M8 16c2 1 6 1 8 0" stroke="oklch(67% 0.06 110)" strokeWidth="1" fill="none" />
      </svg>
    );
  if (mood === "GOOD")
    return (
      <svg className="timeline-mood-icon" viewBox="0 0 24 24" fill="none">
        <circle cx="12" cy="12" r="10" fill="oklch(88% 0.12 80)" />
        <ellipse cx="8" cy="9" rx="1.5" ry="1.5" fill="oklch(72% 0.12 80)" />
        <ellipse cx="16" cy="9" rx="1.5" ry="1.5" fill="oklch(72% 0.12 80)" />
        <path d="M8 15c2 2 6 2 8 0" stroke="oklch(72% 0.12 80)" strokeWidth="1.3" fill="none" />
      </svg>
    );
  if (mood === "AWESOME")
    return (
      <svg className="timeline-mood-icon" viewBox="0 0 24 24" fill="none">
        <circle cx="12" cy="12" r="10" fill="oklch(82% 0.18 80)" />
        <ellipse cx="8" cy="10" rx="2" ry="1.5" fill="oklch(78% 0.18 75)" />
        <ellipse cx="16" cy="10" rx="2" ry="1.5" fill="oklch(78% 0.18 75)" />
        <path d="M8 15c2 2 6 2 8 0" stroke="oklch(78% 0.18 75)" strokeWidth="1.3" fill="none" />
      </svg>
    );
  return null;
}

function formatTime(isoString) {
  const d = new Date(isoString);
  let h = d.getHours();
  const m = String(d.getMinutes()).padStart(2, "0");
  const ampm = h >= 12 ? "PM" : "AM";
  h = h % 12 || 12;
  return `${h}:${m} ${ampm}`;
}

const dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

function formatEntryDate(dateStr) {
  const d = new Date(dateStr + "T00:00:00");
  return `${dayNames[d.getDay()]} ${d.getDate()} ${monthNames[d.getMonth()]} ${d.getFullYear()}`;
}

function CheckInTimelineEntry({ entry, onEdit, onDelete }) {
  const showMoodIcon = true;
  const isPublic = entry.public !== undefined ? entry.public : entry.isPublic;

  return (
    <div className="timeline-entry" data-date={entry.date}>
      <div className="timeline-dot checked" />
      <div className="timeline-date">{formatEntryDate(entry.date)}</div>
      <div className="timeline-entry-card">
        <div className="timeline-entry-head">
          <div className="timeline-entry-head-left">
            {showMoodIcon && <MoodIconSmall mood={entry.mood} />}
            {entry.habit?.categoryName && <span className="badge">{entry.habit.categoryName}</span>}
            {entry.habit?.name && (
              <span style={{ fontFamily: "var(--font-display)", fontSize: "0.9rem" }}>
                {entry.habit.name}
              </span>
            )}
          </div>
          <span
            className="badge"
            style={
              isPublic
                ? { background: "oklch(68% 0.16 75 / 0.08)", borderColor: "oklch(68% 0.16 75 / 0.2)", color: "var(--accent)" }
                : {}
            }
          >
            {isPublic ? "Public" : "Private"}
          </span>
        </div>
        {entry.content && <div className="timeline-content">{entry.content}</div>}
        <div className="timeline-entry-foot">
          <span className="body-sm" style={{ color: "var(--muted)" }}>
            {entry.createdAt ? formatTime(entry.createdAt) : ""}
          </span>
          <div className="timeline-entry-actions">
            <button onClick={() => onEdit(entry)}>Edit</button>
            <button onClick={() => onDelete(entry)}>Delete</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CheckInTimelineEntry;
