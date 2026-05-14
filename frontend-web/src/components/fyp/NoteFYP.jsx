import React from "react";
import MDEditor from "@uiw/react-md-editor";

function NoteFYP({ note }) {
  return (
    <div className="w-full max-w-xl bg-[var(--color-6)] rounded-2xl shadow-xl flex flex-col gap-4 p-6 border border-[var(--color-5)]">
      <h1 className="text-3xl font-bold break-words">{note.title}</h1>
      <div className="note-markdown text-lg rounded-lg p-4">
        <MDEditor.Markdown source={note.content || ""} />
      </div>
      <p className="text-sm text-gray-700">Personal feeling: {note.personalFeeling}</p>
    </div>
  );
}

export default NoteFYP;
