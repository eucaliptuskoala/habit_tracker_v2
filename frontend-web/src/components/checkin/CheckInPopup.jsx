import { useState, useEffect } from "react";
import MoodPicker from "../practice/MoodPicker";

function CheckInPopup({ isOpen, habitId, habitName, habits, onSave, onClose }) {
  const [mood, setMood] = useState(null);
  const [content, setContent] = useState("");
  const [selectedHabitId, setSelectedHabitId] = useState(null);
  const [saving, setSaving] = useState(false);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    return () => {
      setMood(null);
      setContent("");
      setSelectedHabitId(null);
      setSaving(false);
      setSuccess(false);
    };
  }, [isOpen]);

  if (!isOpen) return null;

  const effectiveHabitId = habitId || selectedHabitId;

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!mood || !effectiveHabitId) return;
    setSaving(true);
    await onSave({ habitId: effectiveHabitId, mood, content, public: false });
    setSuccess(true);
    setTimeout(() => {
      onClose();
      setSuccess(false);
      setSaving(false);
      setMood(null);
      setContent("");
      setSelectedHabitId(null);
    }, 600);
  };

  if (success) {
    return (
      <div className="dialog-overlay open">
        <div className="dialog" style={{ maxWidth: "400px", textAlign: "center" }}>
          <div className="popup-success-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round">
              <path d="M20 6L9 17l-5-5" />
            </svg>
          </div>
          <p className="body-lg" style={{ color: "var(--accent)", marginTop: "var(--space-md)" }}>
            Check-in saved!
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="dialog-overlay open" onClick={onClose}>
      <div className="dialog" style={{ maxWidth: "500px" }} onClick={(e) => e.stopPropagation()}>
        <h3>{habitName ? `Check in: ${habitName}` : "New check-in"}</h3>
        <p style={{ marginBottom: "var(--space-lg)" }}>How are you feeling?</p>

        <form onSubmit={handleSubmit}>
          <div className="field">
            <label>Mood</label>
            <MoodPicker value={mood} onChange={setMood} />
          </div>

          {!habitId && habits && (
            <div className="field">
              <label>Practice</label>
              {habits.filter(h => !h.checkedInToday).length === 0 ? (
                <p className="body-sm" style={{ color: "var(--muted)" }}>
                  No practices to check in today.
                </p>
              ) : (
                <div className="checkin-habit-cards">
                  {habits.filter(h => !h.checkedInToday).map(h => (
                    <button
                      key={h.id}
                      type="button"
                      className={`checkin-habit-card${selectedHabitId === h.id ? " selected" : ""}`}
                      onClick={() => setSelectedHabitId(h.id)}
                    >
                      <span className="checkin-habit-card-name">{h.name}</span>
                    </button>
                  ))}
                </div>
              )}
            </div>
          )}

          <div className="field">
            <label htmlFor="popupContent">Reflection (optional)</label>
            <textarea
              id="popupContent"
              className="textarea"
              rows={4}
              value={content}
              onChange={(e) => setContent(e.target.value)}
              placeholder="What's on your mind?"
            />
          </div>

          <div className="dialog-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>Cancel</button>
            <button type="submit" className="btn btn-primary" disabled={!mood || !effectiveHabitId || saving}>
              {saving ? "Saving..." : "Save check-in"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CheckInPopup;
