import { useEffect, useState } from "react";
import CheckInAPI from "../apis/CheckInAPI";
import UserActivityCalendar from "../components/habitprogress/UserActivityCalendar";
import HabitProgressBarChart from "../components/habitprogress/HabitProgressLineChart";

function ProgressPage() {
  const today = new Date().toISOString().slice(0, 10);
  const thirtyDaysAgo = new Date(Date.now() - 30 * 864e5).toISOString().slice(0, 10);

  const [checkIns, setCheckIns] = useState([]);
  const [progressPerHabit, setProgressPerHabit] = useState({});
  const [contribution, setContribution] = useState([]);
  const [startDate, setStartDate] = useState(thirtyDaysAgo);
  const [endDate, setEndDate] = useState(today);
  const [error, setError] = useState("");

  const fetchProgress = () => {
    const from = startDate || undefined;
    const to = endDate || undefined;

    CheckInAPI.getAll(from, to)
      .then((data) => {
        setCheckIns(data);

        const perHabits = {};
        data.forEach((ci) => {
          const name = ci.habit?.name || "Unknown";
          if (!perHabits[name]) perHabits[name] = [];
          perHabits[name].push({ date: ci.date, streakValue: ci.streakValue });
        });
        Object.values(perHabits).forEach((hp) =>
          hp.sort((a, b) => new Date(a.date) - new Date(b.date))
        );
        setProgressPerHabit(perHabits);

        const activity = {};
        data.forEach((ci) => {
          activity[ci.date] = (activity[ci.date] || 0) + 1;
        });
        setContribution(
          Object.entries(activity).map(([day, value]) => ({ day, value }))
        );
      })
      .catch(() => setError("Failed to load progress"));
  };

  useEffect(() => { fetchProgress(); }, []);

  if (error) return <main className="max-w-[1280px] mx-auto px-[var(--gutter)] py-[var(--space-xl)]"><p className="text-solen-mood-awful">{error}</p></main>;

  return (
    <main className="max-w-[1280px] mx-auto px-[var(--gutter)] py-[var(--space-xl)]">
      <div className="mb-[var(--space-xl)] animate-[fade-in_0.5s_ease_both]">
        <span className="font-mono text-[0.75rem] tracking-[0.05em] uppercase text-solen-muted block mb-[var(--space-sm)]">
          Your growth
        </span>
        <h1 className="font-display text-[length:var(--fs-heading)] leading-[1.15] tracking-[-0.015em] font-[400]">Progress</h1>
      </div>

      <div className="flex items-end gap-4 mb-8 animate-[fade-in_0.5s_ease_0.1s_both]">
        <label className="flex flex-col gap-1 font-mono text-[0.7rem] tracking-[0.08em] uppercase text-solen-muted">
          From
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} className="px-3 py-2 border border-solen-border rounded-[8px] bg-solen-surface text-sm text-solen-fg outline-none" />
        </label>
        <label className="flex flex-col gap-1 font-mono text-[0.7rem] tracking-[0.08em] uppercase text-solen-muted">
          To
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} className="px-3 py-2 border border-solen-border rounded-[8px] bg-solen-surface text-sm text-solen-fg outline-none" />
        </label>
        <button className="inline-flex items-center gap-2 px-7 py-3 rounded-[8px] text-[0.95rem] font-medium text-[var(--color-solen-surface)] bg-solen-accent border border-solen-accent no-underline hover:bg-solen-accent-glow hover:border-solen-accent-glow hover:shadow-[0_0_24px_oklch(78%_0.18_80_/_0.25)] transition-all duration-200 cursor-pointer font-body" onClick={fetchProgress}>Apply</button>
      </div>

      <div className="mb-[var(--space-xl)] animate-[fade-in_0.5s_ease_0.1s_both]">
        <h2 className="font-display text-[1.2rem] font-[400] mb-[var(--space-md)]">Activity</h2>
        <UserActivityCalendar data={contribution} startDate={startDate} endDate={endDate} />
      </div>

      <div className="mb-[var(--space-xl)] animate-[fade-in_0.5s_ease_0.2s_both]">
        <h2 className="font-display text-[1.2rem] font-[400] mb-[var(--space-md)]">Per-practice trends</h2>
        <div className="grid gap-4 md:grid-cols-2">
          {Object.entries(progressPerHabit).map(([name, habitProgress]) => (
            <div key={name} className="bg-solen-surface border border-solen-border rounded-[8px] p-[var(--space-lg)]">
              <div className="flex justify-between items-center mb-8">
                <h3 className="font-display text-[1rem] font-[400]">{name}</h3>
                <span className="font-mono text-[0.8rem] text-solen-muted">{habitProgress.length} days tracked</span>
              </div>
              <HabitProgressBarChart data={habitProgress} />
            </div>
          ))}
        </div>
      </div>
    </main>
  );
}

export default ProgressPage;
