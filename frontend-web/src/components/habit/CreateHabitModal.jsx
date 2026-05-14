import React from "react";
import { useState } from "react";
import HabitTemplateSelector from "./HabitStrategySelector";

function CreateHabitModal({ habitTemplates, popularHabitTemplates, handleCreateHabit }) {
  const [open, setOpen] = useState(false);

  return (
    <>
      <button onClick={() => setOpen(true)} className="bg-[var(--color-9)] px-4 py-2 rounded-lg top-20 right-20 hover:bg-[var(--color-8)] text-black">+ New Habit</button>

      {open && (
          <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50">
            <div className="bg-[var(--color-6)] rounded-2xl p-6 w-full max-w-lg shadow-xl">

            <div className="flex justify-between items-start mb-4">
              <div>
                <h2 className="text-xl font-semibold">Create a new habit</h2>
                <p className="text-sm text-gray-600">
                  Choose a template or create your own
                </p>
              </div>

              <button
                onClick={() => setOpen(false)}
                className="text-gray-500 hover:text-black"
              >
                ✕
              </button>
            </div>
            <div className="bg-white rounded-xl p-4 border">
              <HabitTemplateSelector
                habitTemplates={habitTemplates}
                popularHabitTemplates={popularHabitTemplates}
                onSubmit={(req) => { handleCreateHabit(req); setOpen(false); }}
              />
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default CreateHabitModal;
