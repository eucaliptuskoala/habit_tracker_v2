import React from "react";
import { useState } from "react";
import NoteContentEditor from "./NoteContentEditor";
import HabitCardList from "../habit/HabitCardList";

function CreateNoteForm({handleCreateNote, userHabits, createNoteRequest, setCreateNoteRequest, habitId}) {

  const [selectedHabit, setSelectedHabit] = useState(habitId || createNoteRequest.habitId || null);

  const updateRequest = (patch) =>
    setCreateNoteRequest(prev => ({ ...prev, ...patch }));

  const selectHabit = (habitId) => {
    setSelectedHabit(habitId);
    updateRequest({ habitId: habitId });

  }

  return (
    <div className="bg-[var(--color-6)] rounded-2xl p-6 shadow-xl w-full max-w-4xl max-h-[90vh] overflow-y-auto relative">
      <h2 className="text-xl font-semibold mb-4">Create Note</h2>

      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
          <input
            type="text"
            className="w-full px-4 py-2 rounded-xl border border-[var(--color-8)]
                       focus:ring-2 focus:ring-[var(--color-9)] bg-white"
            value={createNoteRequest.title}
            onChange={(e) => updateRequest({ title: e.target.value })}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Content</label>
          <NoteContentEditor
            value={createNoteRequest.content}
            onChange={(val) => updateRequest({ content: val || "" })}
          />
        </div>

        {!habitId && (
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Select Habit</label>
            
            {userHabits.length > 0 ? (
              <HabitCardList habits={userHabits} selectHabit={selectHabit} selected={selectedHabit} />
            ) : (
              <p className="text-sm text-gray-500 italic">
                First create a habit for yourself.
              </p>
            )}
          </div>
        )}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Personal Feeling</label>
          <input
            type="number"
            min="0"
            max="10"
            className="w-full px-4 py-2 rounded-xl border border-[var(--color-8)]
                      focus:ring-2 focus:ring-[var(--color-9)] bg-white"
            value={createNoteRequest.personalFeeling ?? ""}
            onChange={(e) => {
              let val = Number(e.target.value);

              // Clamp value between 0 and 10
              if (isNaN(val)) val = "";
              else if (val > 10) val = 10;
              else if (val < 0) val = 0;

              updateRequest({ personalFeeling: val });
            }}
          />
        </div>

        <div className="flex flex-col gap-1">
          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={createNoteRequest.isPublic}
              onChange={(e) => updateRequest({ isPublic: e.target.checked })}
              className="w-4 h-4 accent-[var(--color-9)]"
            />
            <label className="text-sm text-gray-700 font-medium">Make this note public?</label>
          </div>
          <p className="text-xs text-gray-500">
            Your public notes would be seen by other users with the same habits in FYP.
          </p>
        </div>

        <button
          onClick={handleCreateNote}
          disabled={!selectedHabit && !habitId}
          className={`w-full py-2 rounded-xl font-medium transition
                      ${(!selectedHabit && !habitId)
                        ? "bg-gray-200 text-gray-400 cursor-not-allowed"
                        : "bg-[var(--color-9)] hover:bg-[var(--color-8)] text-white"}`
          }
        >
          Create Note
        </button>
      </div>
    </div>
  );
}

export default CreateNoteForm;