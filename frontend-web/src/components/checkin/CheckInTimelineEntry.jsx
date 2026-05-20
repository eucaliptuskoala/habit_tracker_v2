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
    <div className="relative pl-[52px] pb-8 last:pb-0" data-date={entry.date}>
      <div className="absolute left-2 top-1 w-[18px] h-[18px] rounded-full border-2 border-solen-accent bg-solen-accent flex items-center justify-center" />
      <div className="font-mono text-[0.75rem] text-solen-muted mb-[var(--space-xs)]">{formatEntryDate(entry.date)}</div>
      <div className="bg-solen-surface border border-solen-border rounded-[8px] p-[var(--space-lg)] transition-[border-color] duration-200 hover:border-solen-accent-dim">
        <div className="flex items-center justify-between gap-[var(--space-sm)] mb-[var(--space-sm)] flex-wrap">
          <div className="flex items-center gap-[var(--space-sm)] flex-wrap">
            <span className="w-6 h-6 shrink-0"><MoodIcon mood={entry.mood} size={24} /></span>
            {entry.habit?.categoryName && <span className="inline-flex items-center px-[10px] py-[2px] font-mono text-[0.7rem] tracking-[0.03em] rounded-full border border-solen-border text-solen-muted bg-solen-surface">{entry.habit.categoryName}</span>}
            {entry.habit?.name && (
              <span className="font-display text-[0.9rem]">
                {entry.habit.name}
              </span>
            )}
          </div>
          <span
            className={`inline-flex items-center px-[10px] py-[2px] font-mono text-[0.7rem] tracking-[0.03em] rounded-full border ${isPublic ? "bg-solen-accent-subtle border-solen-accent/20 text-solen-accent" : "border-solen-border text-solen-muted bg-solen-surface"}`}
          >
            {isPublic ? "Public" : "Private"}
          </span>
        </div>
        {entry.content && <div className="text-[0.95rem] leading-[1.7] text-solen-fg whitespace-pre-wrap empty:hidden">{entry.content}</div>}
        <div className="flex items-center justify-between mt-[var(--space-md)] pt-[var(--space-md)] border-t border-solen-border">
          <span className="font-body text-[0.875rem] leading-[1.5] text-solen-muted">
            {entry.createdAt ? formatTime(entry.createdAt) : ""}
          </span>
          <div className="flex gap-[var(--space-sm)]">
            <button className="bg-transparent border-none cursor-pointer font-body text-[0.8rem] text-solen-muted px-2 py-1 rounded-[4px] transition-all duration-150 hover:bg-solen-surface-soft hover:text-solen-fg" onClick={() => onEdit(entry)}>Edit</button>
            <button className="bg-transparent border-none cursor-pointer font-body text-[0.8rem] text-solen-muted px-2 py-1 rounded-[4px] transition-all duration-150 hover:bg-solen-surface-soft hover:text-solen-fg" onClick={() => onDelete(entry)}>Delete</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CheckInTimelineEntry;
