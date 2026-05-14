import React from "react";
import HabitCard from "./HabitCard";

export default function HabitCardList({ habits, selectHabit, selected }) {
  const [selectedHabitId, setSelectedHabitId] = React.useState(selected || null);

  const handleSelect = (habitId) => {
    const newId = habitId === selectedHabitId ? null : habitId;
    setSelectedHabitId(newId);
    selectHabit(newId);
  };

  return (
    <div className="grid grid-cols-2 md:grid-cols-3 gap-4 max-h-96 overflow-y-auto">
      {habits.map(habit => (
        <HabitCard
          key={habit.id}
          habit={habit}
          isSelected={habit.id === selectedHabitId}
          onSelect={() => handleSelect(habit.id)}
        />
      ))}
    </div>
  );
}
