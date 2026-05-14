import React, { useState } from "react";
import MDEditor from "@uiw/react-md-editor";

function Note({ note, onEdit, handleDeleteNote}) {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

return (
    <div className="relative bg-[var(--color-6)] rounded-2xl shadow-md border border-[var(--color-5)] p-5 flex flex-col gap-4 hover:shadow-lg transition">
      
      <div className="absolute top-3 right-3">
        <button
          className="p-1 rounded hover:bg-gray-300/40"
          onClick={() => setIsMenuOpen(prev => !prev)}
        >
          •••
        </button>
        {isMenuOpen && (
          <div className="absolute right-0 mt-2 w-36 bg-white border border-gray-200 rounded-lg shadow-md z-20 overflow-hidden">
            <button
              className="w-full px-3 py-2 text-left text-gray-800 hover:bg-gray-100"
              onClick={() => { onEdit(note); setIsMenuOpen(false); }}
            >
              Update
            </button>
            <button
              className="w-full px-3 py-2 text-left text-red-600 hover:bg-red-100"
              onClick={() => { handleDeleteNote(note.id); setIsMenuOpen(false); }}
            >
              Delete
            </button>
          </div>
        )}
      </div>

      <h2 className="text-lg font-semibold break-words">{note.title}</h2>

      <div className="note-markdown text-sm rounded-lg p-3">
        <MDEditor.Markdown source={note.content || ""} />
      </div>

      <p className="text-xs text-gray-500">Personal feeling: {note.personalFeeling}</p>
    </div>
  );
}

export default Note;
