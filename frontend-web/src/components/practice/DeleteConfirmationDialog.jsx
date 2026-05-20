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
    <div className="fixed inset-0 bg-black/20 flex items-center justify-center z-200" onClick={onCancel}>
      <div className="bg-solen-surface border border-solen-border rounded-[8px] p-[var(--space-xl)] max-w-[400px] w-[90%] shadow-[0_16px_48px_rgb(0_0_0_/_0.08)]" onClick={(e) => e.stopPropagation()}>
        <h3 className="font-display text-[1.15rem] font-[400] mb-[var(--space-sm)]">Delete practice?</h3>
        <p className="text-[0.9rem] text-solen-muted mb-[var(--space-lg)] leading-[1.6]">
          Are you sure you want to delete "{practiceName}"? This will remove the practice
          and all its check-ins. This can't be undone.
        </p>
        <div className="flex gap-[var(--space-sm)] justify-end">
          <button className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-solen-fg no-underline hover:bg-solen-surface-soft transition-all duration-200 cursor-pointer font-body bg-transparent border-none" onClick={onCancel}>Cancel</button>
          <button className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-white bg-solen-danger border border-solen-danger no-underline hover:bg-solen-danger-hover transition-all duration-200 cursor-pointer font-body" onClick={onConfirm}>Delete</button>
        </div>
      </div>
    </div>
  );
}

export default DeleteConfirmationDialog;
