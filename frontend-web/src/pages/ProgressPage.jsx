import { useEffect, useState } from "react";
import HabitProgressAPI from "../apis/HabitProgressAPI";
import UserActivityCalendar from "../components/habitprogress/UserActivityCalendar";
import HabitProgressBarChart from "../components/habitprogress/HabitProgressLineChart";

function ProgressPage() {
  const [progress, setProgress] = useState([]);
  const [progressPerHabit, setProgressPerHabit] = useState({});
  const [contribution, setContribution] = useState([]);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    HabitProgressAPI.getProgressByUser()
      .then((data) => {
        setProgress(data);

        const dates = data.map((p) => p.date).sort();
        if (dates.length > 0) {
          setStartDate(dates[0]);
          setEndDate(dates[dates.length - 1]);
        }

        const perHabits = {};
        data.forEach((p) => {
          const name = p.habit?.name || "Unknown";
          if (!perHabits[name]) perHabits[name] = [];
          perHabits[name].push(p);
        });
        Object.values(perHabits).forEach((hp) =>
          hp.sort((a, b) => new Date(a.date) - new Date(b.date))
        );
        setProgressPerHabit(perHabits);

        const activity = {};
        data.forEach((p) => {
          if (p.streakValue == null) return;
          activity[p.date] = (activity[p.date] || 0) + 1;
        });
        setContribution(
          Object.entries(activity).map(([day, value]) => ({ day, value }))
        );
      })
      .catch(() => setError("Failed to load progress"));
  }, []);

  if (error) return <main className="page"><p style={{ color: "var(--mood-awful)" }}>{error}</p></main>;
  if (!progress.length) return <main className="page"><p style={{ color: "var(--muted)" }}>Loading...</p></main>;

  return (
    <main className="page">
      <div className="progress-header animate-in">
        <span className="label" style={{ display: "block", marginBottom: "var(--space-sm)" }}>
          Your growth
        </span>
        <h1 className="display-lg">Progress</h1>
      </div>

      <div className="chart-section animate-in animate-in-d1">
        <h2>Activity</h2>
        <UserActivityCalendar data={contribution} startDate={startDate} endDate={endDate} />
      </div>

      <div className="chart-section animate-in animate-in-d2">
        <h2>Per-practice trends</h2>
        <div className="practice-charts">
          {Object.entries(progressPerHabit).map(([name, habitProgress]) => (
            <div key={name} className="chart-container">
              <div className="chart-title">
                <h3>{name}</h3>
                <span className="stat">{habitProgress.length} days tracked</span>
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
