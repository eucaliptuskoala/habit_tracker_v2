function PracticeCard({ practice, onDone, onDelete }) {
  return (
    <div className="practice-card">
      <div className="practice-card-header">
        <div>
          <div className="practice-card-name">{practice.name}</div>
          <div className="practice-card-meta">
            {practice.categoryName && <span className="badge">{practice.categoryName}</span>}
            <span className="streak-indicator">
              <svg viewBox="0 0 16 16" width="14" height="14">
                <circle cx="8" cy="8" r="6" fill="oklch(68% 0.16 75 / 0.2)" />
                <circle cx="8" cy="8" r="3" fill="var(--accent)" />
              </svg>
              {practice.streak} days
            </span>
          </div>
        </div>
        <div style={{ display: "flex", alignItems: "center", gap: "4px" }}>
          <button
            className="practice-card-done"
            onClick={() => onDone(practice.id)}
            title="Mark done for today"
          >
            <svg viewBox="0 0 16 16" width="14" height="14" stroke="var(--muted)" strokeWidth="2" fill="none" strokeLinecap="round">
              <path d="M3 8l3 4 7-7" />
            </svg>
          </button>
          <button
            className="practice-card-menu"
            onClick={() => onDelete(practice)}
            title="Delete practice"
          >
            &bull;&bull;&bull;
          </button>
        </div>
      </div>
      {practice.description && (
        <div className="body-sm" style={{ color: "var(--muted)" }}>
          {practice.description}
        </div>
      )}
    </div>
  );
}

export default PracticeCard;
