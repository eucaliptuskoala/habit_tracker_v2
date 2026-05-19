import MoodIcon from "../MoodIcon";

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
  const isPublic = entry.public !== undefined ? entry.public : entry.isPublic;

  return (
    <div className="timeline-entry" data-date={entry.date}>
      <div className="timeline-dot checked" />
      <div className="timeline-date">{formatEntryDate(entry.date)}</div>
      <div className="timeline-entry-card">
        <div className="timeline-entry-head">
          <div className="timeline-entry-head-left">
            <span className="timeline-mood-icon"><MoodIcon mood={entry.mood} size={24} /></span>
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
