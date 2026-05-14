import React, { useState } from "react";
import CreateNoteForm from "./CreateNoteForm";

function CreateNoteModal({
  handleCreateNote,
  userHabits,
  createNoteRequest,
  setCreateNoteRequest,
}) {
  const [open, setOpen] = useState(false);

  const handleClose = () => setOpen(false);

  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      handleClose();
    }
  };

  return (
    <>
      <button
        onClick={() => setOpen(true)}
        className="bg-[var(--color-9)] px-4 py-2 rounded-lg hover:bg-[var(--color-8)] text-black font-medium"
      >
        + New Note
      </button>

      {open && (
        <div
          className="fixed inset-0 bg-black/40 backdrop-blur-sm z-50 flex items-center justify-center p-4"
          onClick={handleOverlayClick}
        >
          {/* Skip/Close button fixed relative to viewport */}
          <button
            onClick={handleClose}
            className="fixed top-6 right-6 z-60 text-gray-500 hover:text-gray-800 font-bold text-xl bg-white rounded-full w-10 h-10 flex items-center justify-center shadow-md"
          >
            ×
          </button>

          <CreateNoteForm
            handleCreateNote={handleCreateNote}
            userHabits={userHabits}
            createNoteRequest={createNoteRequest}
            setCreateNoteRequest={setCreateNoteRequest}
          />
        </div>
      )}
    </>
  );
}

export default CreateNoteModal;
