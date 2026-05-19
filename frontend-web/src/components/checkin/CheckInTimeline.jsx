import { useState, useMemo } from "react";
import CheckInTimelineEntry from "./CheckInTimelineEntry";

function isThisWeek(dateStr) {
  const d = new Date(dateStr + "T00:00:00");
  const now = new Date();
  const startOfWeek = new Date(now);
  startOfWeek.setDate(now.getDate() - now.getDay());
  startOfWeek.setHours(0, 0, 0, 0);
  return d >= startOfWeek;
}

function isThisMonth(dateStr) {
  const d = new Date(dateStr + "T00:00:00");
  const now = new Date();
  return d.getMonth() === now.getMonth() && d.getFullYear() === now.getFullYear();
}

const filters = [
  { key: "all", label: "All" },
  { key: "this-week", label: "This week" },
  { key: "this-month", label: "This month" },
];

function CheckInTimeline({ entries, onEdit, onDelete, onCreate }) {
  const [activeFilter, setActiveFilter] = useState("all");

  const filtered = useMemo(() => {
    if (activeFilter === "this-week") return entries.filter((e) => isThisWeek(e.date));
    if (activeFilter === "this-month") return entries.filter((e) => isThisMonth(e.date));
    return entries;
  }, [entries, activeFilter]);

  const sorted = [...filtered].sort((a, b) => new Date(b.date) - new Date(a.date));

  return (
    <>
      <div className="timeline-header animate-in">
        <div>
          <span className="label" style={{ display: "block", marginBottom: "var(--space-sm)" }}>
            Your journal
          </span>
          <h1 className="display-lg">Check-Ins</h1>
        </div>
        <div className="timeline-filters">
          {filters.map((f) => (
            <button
              key={f.key}
              className={`filter-btn${activeFilter === f.key ? " active" : ""}`}
              onClick={() => setActiveFilter(f.key)}
            >
              {f.label}
            </button>
          ))}
        </div>
        <button className="btn btn-ghost btn-sm" onClick={onCreate}>
          + New check-in
        </button>
      </div>

      {sorted.length === 0 ? (
        <div className="timeline-empty">
          <p className="body-lg" style={{ marginBottom: "var(--space-md)" }}>No check-ins yet</p>
          <p className="body-sm" style={{ color: "var(--muted)" }}>
            Your daily reflections will appear here once you start checking in.
          </p>
        </div>
      ) : (
        <div className="timeline animate-in animate-in-d1">
          {sorted.map((entry) => (
            <CheckInTimelineEntry
              key={entry.id}
              entry={entry}
              onEdit={onEdit}
              onDelete={onDelete}
            />
          ))}
        </div>
      )}
    </>
  );
}

export default CheckInTimeline;
