import React, { useState } from "react";

function Habit({habit, handleUpdate, handleDelete}) {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const now = new Date();
  const lastUpdatedAt = new Date(habit.lastUpdatedStreak);

  const canBeUpdated = lastUpdatedAt.getFullYear() === now.getFullYear() &&
    lastUpdatedAt.getMonth() === now.getMonth() &&
    lastUpdatedAt.getDate() === now.getDate();

    return(
        <div className="relative bg-[var(--color-6)] rounded-2xl border border-[var(--color-5)]
                shadow-sm p-5 flex flex-col justify-between min-h-[220px]">
        <div className="absolute top-3 right-3">
          <button
            onClick={() => setIsMenuOpen(p => !p)}
            className="text-gray-500 hover:text-black"
          >
            ⋮
          </button>

          {isMenuOpen && (
            <div className="absolute right-0 mt-2 bg-white rounded-xl shadow-md border w-36 z-20">
              <button
                onClick={() => handleDelete(habit.id)}
                className="w-full px-4 py-2 text-sm text-red-600 hover:bg-red-50 rounded-xl"
              >
                Delete habit
              </button>
            </div>
          )}
        </div>

        <div>
          <h2 className="text-lg font-semibold mb-1">{habit.name}</h2>
          <p className="text-sm text-gray-600 mb-4">{habit.description}</p>

          <div className="flex items-center gap-2 text-sm">
            <span className="font-medium">🔥 {habit.streak}</span>
            <span className="text-gray-500">day streak</span>
          </div>
        </div>

        <button
          disabled={canBeUpdated}
          onClick={() => handleUpdate(habit.id)}
          className={`mt-6 w-full py-2 rounded-xl text-sm font-medium transition
            ${canBeUpdated
              ? "bg-gray-200 text-gray-400 cursor-not-allowed"
              : "bg-[var(--color-9)] hover:bg-[var(--color-8)]"
            }`}
        >
          {canBeUpdated ? "Already updated today" : "Mark as done"}
        </button>
      </div>

    );
}

export default Habit;