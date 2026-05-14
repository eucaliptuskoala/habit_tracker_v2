import React from "react";

export default function HabitCard({ habit, isSelected, onSelect }) {
  return (
    <div
      className={`p-4 border rounded-2xl cursor-pointer transition-all
                  ${isSelected ? "bg-[var(--color-4)] border-[var(--color-9)] shadow-lg" : "bg-white hover:bg-[var(--color-5)]"} 
                  flex items-center justify-center font-medium text-gray-800`}
      onClick={onSelect}
    >
      {habit.name}
    </div>
  );
}
