import { useState, useEffect } from "react";
import MoodPicker from "../practice/MoodPicker";

function EditCheckInModal({ isOpen, entry, onSave, onClose }) {
  const [content, setContent] = useState("");
  const [mood, setMood] = useState(null);
  const [isPublic, setIsPublic] = useState(false);

  useEffect(() => {
    if (entry) {
      setContent(entry.content || "");
      setMood(entry.mood || null);
      setIsPublic(entry.public !== undefined ? entry.public : entry.isPublic || false);
    }
  }, [entry]);

  if (!isOpen || !entry) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(entry.id, { content, mood, public: isPublic });
  };

  return (
    <div className="dialog-overlay open" onClick={onClose}>
      <div className="dialog" style={{ maxWidth: "500px" }} onClick={(e) => e.stopPropagation()}>
        <h3>Edit check-in</h3>
        <p style={{ marginBottom: "var(--space-lg)" }}>Update your mood or reflection.</p>

        <form onSubmit={handleSubmit}>
          <div className="field">
            <label>Mood</label>
            <MoodPicker value={mood} onChange={setMood} />
          </div>

          <div className="field">
            <label htmlFor="editContent">Reflection</label>
            <textarea
              id="editContent"
              className="textarea"
              rows={4}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </div>

          <div className="field" style={{ display: "flex", alignItems: "center", gap: "var(--space-sm)" }}>
            <input
              type="checkbox"
              id="editPublic"
              checked={isPublic}
              onChange={(e) => setIsPublic(e.target.checked)}
              style={{ width: "18px", height: "18px", accentColor: "var(--accent)" }}
            />
            <label htmlFor="editPublic" style={{ margin: 0, textTransform: "none", fontFamily: "var(--font-body)", fontSize: "0.9rem", color: "var(--fg)" }}>
              Make this check-in public on Inspire
            </label>
          </div>

          <div className="dialog-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>Cancel</button>
            <button type="submit" className="btn btn-primary">Save</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EditCheckInModal;
