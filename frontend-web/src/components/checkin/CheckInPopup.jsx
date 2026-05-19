import { useState } from "react";
import MoodPicker from "../practice/MoodPicker";

function CheckInPopup({ isOpen, habitId, habitName, onSave, onClose }) {
  const [mood, setMood] = useState(null);
  const [content, setContent] = useState("");
  const [saving, setSaving] = useState(false);

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!mood) return;
    setSaving(true);
    await onSave({ habitId, mood, content, public: false });
    setSaving(false);
    setMood(null);
    setContent("");
  };

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
            <button type="submit" className="btn btn-primary" disabled={!mood || saving}>
              {saving ? "Saving..." : "Save check-in"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CheckInPopup;
