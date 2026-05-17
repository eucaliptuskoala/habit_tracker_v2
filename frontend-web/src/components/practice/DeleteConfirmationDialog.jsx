import { useEffect } from "react";

function DeleteConfirmationDialog({ isOpen, practiceName, onCancel, onConfirm }) {
  useEffect(() => {
    if (isOpen) {
      const handleKey = (e) => { if (e.key === "Escape") onCancel(); };
      document.addEventListener("keydown", handleKey);
      return () => document.removeEventListener("keydown", handleKey);
    }
  }, [isOpen, onCancel]);

  if (!isOpen) return null;

  return (
    <div className="dialog-overlay open" onClick={onCancel}>
      <div className="dialog" onClick={(e) => e.stopPropagation()}>
        <h3>Delete practice?</h3>
        <p>
          Are you sure you want to delete "{practiceName}"? This will remove the practice
          and all its check-ins. This can't be undone.
        </p>
        <div className="dialog-actions">
          <button className="btn btn-ghost" onClick={onCancel}>Cancel</button>
          <button className="btn btn-danger" onClick={onConfirm}>Delete</button>
        </div>
      </div>
    </div>
  );
}

export default DeleteConfirmationDialog;
