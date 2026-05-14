import React from "react";
import CreateNoteForm from "./CreateNoteForm";

function NoteModalWrapper({
  isOpen,
  onClose,
  habitId,
  createNoteRequest,
  setCreateNoteRequest,
  handleCreateNote,
  userHabits
}) {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4">
      <button
        onClick={onClose}
        className="fixed top-6 right-6 z-60 text-gray-500 hover:text-gray-800 font-bold text-xl bg-white rounded-full w-10 h-10 flex items-center justify-center shadow-md"
      >
        ×
      </button>
      <CreateNoteForm
        habitId={habitId}
        createNoteRequest={createNoteRequest}
        setCreateNoteRequest={setCreateNoteRequest}
        handleCreateNote={handleCreateNote}
        userHabits={userHabits}
      />
    </div>
  );
}

export default NoteModalWrapper;
