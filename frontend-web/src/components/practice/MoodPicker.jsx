function MoodIcon({ mood, size = 32 }) {
  const s = size;
  if (mood === "AWFUL")
    return (
      <svg viewBox="0 0 32 32" width={s} height={s} fill="none">
        <circle cx="16" cy="16" r="13" fill="oklch(85% 0.06 250)" />
        <path d="M6 6l4 4m-4 0l4-4" stroke="oklch(55% 0.08 250)" strokeWidth="1.5" strokeLinecap="round" />
        <ellipse cx="12" cy="14" rx="2" ry="2.5" fill="oklch(55% 0.08 250)" opacity="0.4" />
        <ellipse cx="20" cy="14" rx="2" ry="2.5" fill="oklch(55% 0.08 250)" opacity="0.4" />
        <path d="M10 22c2-1.5 6-1.5 8 0" stroke="oklch(55% 0.08 250)" strokeWidth="1.2" fill="none" />
        <path d="M23 9l-3 2m3 0l-3-2" stroke="oklch(55% 0.08 250)" strokeWidth="1.2" strokeLinecap="round" />
      </svg>
    );
  if (mood === "BAD")
    return (
      <svg viewBox="0 0 32 32" width={s} height={s} fill="none">
        <circle cx="16" cy="16" r="13" fill="oklch(88% 0.05 230)" />
        <path d="M10 8l2 2m-2 0l2-2" stroke="oklch(62% 0.07 220)" strokeWidth="1.2" strokeLinecap="round" />
        <ellipse cx="12" cy="14" rx="2" ry="2.5" fill="oklch(62% 0.07 220)" opacity="0.3" />
        <ellipse cx="20" cy="14" rx="2" ry="2.5" fill="oklch(62% 0.07 220)" opacity="0.3" />
        <path d="M11 21c2-1 6-1 8 0" stroke="oklch(62% 0.07 220)" strokeWidth="1.2" fill="none" />
        <path d="M26 8l-2 3" stroke="oklch(62% 0.07 220)" strokeWidth="1.2" strokeLinecap="round" />
      </svg>
    );
  if (mood === "OKAY")
    return (
      <svg viewBox="0 0 32 32" width={s} height={s} fill="none">
        <circle cx="16" cy="16" r="13" fill="oklch(90% 0.05 100)" />
        <ellipse cx="11" cy="13" rx="2" ry="2.5" fill="oklch(67% 0.06 110)" opacity="0.35" />
        <ellipse cx="21" cy="13" rx="2" ry="2.5" fill="oklch(67% 0.06 110)" opacity="0.35" />
        <path d="M10 20c2 1.5 6 1.5 8 0" stroke="oklch(67% 0.06 110)" strokeWidth="1.2" fill="none" />
        <circle cx="26" cy="12" r="1.5" fill="oklch(67% 0.06 110)" opacity="0.5" />
      </svg>
    );
  if (mood === "GOOD")
    return (
      <svg viewBox="0 0 32 32" width={s} height={s} fill="none">
        <circle cx="16" cy="16" r="13" fill="oklch(88% 0.12 80)" />
        <ellipse cx="11" cy="12" rx="2" ry="2" fill="oklch(72% 0.12 80)" />
        <ellipse cx="21" cy="12" rx="2" ry="2" fill="oklch(72% 0.12 80)" />
        <path d="M11 19c2 2 6 2 8 0" stroke="oklch(72% 0.12 80)" strokeWidth="1.3" fill="none" />
        <path d="M24 7l2 2-2 2" stroke="oklch(72% 0.12 80)" strokeWidth="1.3" strokeLinecap="round" strokeLinejoin="round" />
        <path d="M27 7l2 2-2 2" stroke="oklch(72% 0.12 80)" strokeWidth="1.3" strokeLinecap="round" strokeLinejoin="round" />
      </svg>
    );
  if (mood === "AWESOME")
    return (
      <svg viewBox="0 0 32 32" width={s} height={s} fill="none">
        <circle cx="16" cy="16" r="13" fill="oklch(82% 0.18 80)" />
        <circle cx="16" cy="16" r="11" fill="oklch(78% 0.18 75)" opacity="0.3" />
        <ellipse cx="11" cy="12" rx="2.5" ry="1.5" fill="oklch(78% 0.18 75)" />
        <ellipse cx="21" cy="12" rx="2.5" ry="1.5" fill="oklch(78% 0.18 75)" />
        <path d="M11 18c2 3 6 3 8 0" stroke="oklch(78% 0.18 75)" strokeWidth="1.5" fill="none" strokeLinecap="round" />
        <path d="M24 4l4 3-4 2" stroke="oklch(78% 0.18 75)" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
        <path d="M27 2l4 3-4 2" stroke="oklch(78% 0.18 75)" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
        <path d="M21 4l4 3-4 2" stroke="oklch(78% 0.18 75)" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
      </svg>
    );
  return null;
}

const moods = ["AWFUL", "BAD", "OKAY", "GOOD", "AWESOME"];

function MoodPicker({ value, onChange }) {
  return (
    <div className="mood-picker">
      {moods.map((mood) => (
        <button
          key={mood}
          type="button"
          className={`mood-btn${value === mood ? " selected" : ""}`}
          onClick={() => onChange(mood)}
        >
          <MoodIcon mood={mood} />
          <span>{mood.charAt(0) + mood.slice(1).toLowerCase()}</span>
        </button>
      ))}
    </div>
  );
}

export default MoodPicker;
