import React from "react";
import NoteContentEditor from "./NoteContentEditor";

function UpdateNoteForm({handleUpdateNote, updateNoteRequest, setUpdateNoteRequest, note, onClose}) {

  return (
    <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex justify-center items-center z-50">
      <div className="w-full max-w-4xl">
        <div className="bg-[var(--color-6)] rounded-2xl p-6 shadow-xl space-y-4 relative">
          <h2 className="text-xl font-semibold">Update Note</h2>
          <button
            onClick={onClose}
            className="absolute top-4 right-4 text-gray-600 hover:text-black text-xl font-bold"
          >
            ×
          </button>

          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
              <input
                type="text"
                className="w-full px-4 py-2 rounded-xl border border-[var(--color-8)] focus:ring-2 focus:ring-[var(--color-9)] bg-white"
                value={updateNoteRequest.title ?? ""}
                onChange={(e) => setUpdateNoteRequest(prev => ({ ...prev, title: e.target.value }))}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Content</label>
              <NoteContentEditor
                value={updateNoteRequest.content ?? ""}
                onChange={(val) => setUpdateNoteRequest(prev => ({ ...prev, content: val ?? "" }))}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Personal Feeling</label>
              <input
                type="number"
                max="10"
                className="w-full px-4 py-2 rounded-xl border border-[var(--color-8)] focus:ring-2 focus:ring-[var(--color-9)] bg-white"
                value={updateNoteRequest.personalFeeling ?? 0}
                onChange={(e) => setUpdateNoteRequest(prev => ({ ...prev, personalFeeling: Number(e.target.value) }))}
              />
            </div>

            <div className="flex items-center gap-2">
              <input
                type="checkbox"
                checked={!!updateNoteRequest.isPublic}
                onChange={(e) => setUpdateNoteRequest(prev => ({ ...prev, isPublic: e.target.checked }))}
                className="w-4 h-4 accent-[var(--color-9)]"
              />
              <label className="text-sm text-gray-700 font-medium">Make this note public?</label>
            </div>

            <button
              onClick={() => { handleUpdateNote(updateNoteRequest, note.id); onClose(); }}
              className="w-full py-2 rounded-xl bg-[var(--color-9)] hover:bg-[var(--color-8)] text-white font-medium transition"
            >
              Update Note
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpdateNoteForm;