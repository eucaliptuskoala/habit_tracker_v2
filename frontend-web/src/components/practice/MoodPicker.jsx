import { useState } from "react";
import MoodIcon from "../MoodIcon";

const moods = ["AWFUL", "BAD", "OKAY", "GOOD", "AWESOME"];

function MoodPicker({ value, onChange }) {
  const [open, setOpen] = useState(false);

  const formatMood = (m) => m.charAt(0) + m.slice(1).toLowerCase();

  return (
    <div className="mood-picker-dropdown" onBlur={(e) => { if (!e.currentTarget.contains(e.relatedTarget)) setOpen(false); }}>
      <button type="button" className="mood-picker-trigger" onClick={() => setOpen(!open)}>
        {value ? (
          <>
            <MoodIcon mood={value} size={24} />
            <span>{formatMood(value)}</span>
          </>
        ) : (
          <span style={{ color: "var(--muted)" }}>Select mood</span>
        )}
        <svg className={`mood-chevron${open ? " open" : ""}`} viewBox="0 0 16 16" width="14" height="14" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
          <path d="M4 6l4 4 4-4" />
        </svg>
      </button>
      {open && (
        <div className="mood-picker-options">
          {moods.map((mood) => (
            <button
              key={mood}
              type="button"
              className={`mood-option${value === mood ? " selected" : ""}`}
              onClick={() => { onChange(mood); setOpen(false); }}
            >
              <MoodIcon mood={mood} size={24} />
              <span>{formatMood(mood)}</span>
            </button>
          ))}
        </div>
      )}
    </div>
  );
}

export default MoodPicker;
