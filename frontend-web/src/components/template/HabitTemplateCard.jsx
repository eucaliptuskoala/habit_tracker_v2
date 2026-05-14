import React from "react";

export default function HabitTemplateCard({ t, createFromTemplate }) {
  return (
    <div
    onClick={() => createFromTemplate(t)}
    className="p-4 rounded-xl border border-[var(--color-5)]
                bg-white hover:bg-[var(--color-2)]
                cursor-pointer transition"
    >
    <p className="font-medium">{t.name}</p>
    {t.popularity && (
        <p className="text-xs text-gray-500 mt-1">
        🔥 Popular with {t.popularity} users
        </p>
    )}
    </div>
  );
}
