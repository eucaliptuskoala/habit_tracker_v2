import { useState } from "react";
import MoodIcon from "../MoodIcon";

const moods = ["AWFUL", "BAD", "OKAY", "GOOD", "AWESOME"];

function MoodPicker({ value, onChange }) {
  const [open, setOpen] = useState(false);

  const formatMood = (m) => m.charAt(0) + m.slice(1).toLowerCase();

  return (
    <div className="relative w-full" onBlur={(e) => { if (!e.currentTarget.contains(e.relatedTarget)) setOpen(false); }}>
      <button type="button" className="flex items-center gap-2 w-full px-3.5 py-2.5 border border-solen-border rounded-[8px] bg-solen-surface cursor-pointer font-body text-sm text-solen-fg transition-colors duration-200 hover:border-solen-accent-dim" onClick={() => setOpen(!open)}>
        {value ? (
          <>
            <MoodIcon mood={value} size={24} />
            <span>{formatMood(value)}</span>
          </>
        ) : (
          <span style={{ color: "var(--color-solen-muted)" }}>Select mood</span>
        )}
        <svg className={`ml-auto transition-transform duration-200 text-solen-muted${open ? " rotate-180" : ""}`} viewBox="0 0 16 16" width="14" height="14" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
          <path d="M4 6l4 4 4-4" />
        </svg>
      </button>
      {open && (
        <div className="absolute top-full left-0 right-0 mt-1 bg-solen-surface border border-solen-border rounded-[8px] shadow-[0_8px_32px_rgb(0_0_0_/_0.06)] z-50 overflow-hidden">
          {moods.map((mood) => (
            <button
              key={mood}
              type="button"
              className={`flex items-center gap-2 w-full px-3.5 py-2.5 border-none bg-transparent cursor-pointer font-body text-sm text-solen-fg transition-colors duration-150 hover:bg-solen-surface-soft${value === mood ? " bg-solen-accent-subtle text-solen-accent" : ""}`}
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
