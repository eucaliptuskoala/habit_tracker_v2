import React, { useEffect, useState } from "react";
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

  if (error) return <main className="page"><p style={{ color: "var(--mood-awful)" }}>{error}</p></main>;

  return (
    <main className="page">
      <div className="progress-header animate-in">
        <span className="label" style={{ display: "block", marginBottom: "var(--space-sm)" }}>
          Your growth
        </span>
        <h1 className="display-lg">Progress</h1>
      </div>

      <div className="date-range animate-in animate-in-d1">
        <label>
          From
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
        </label>
        <label>
          To
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
        </label>
        <button className="btn btn-primary" onClick={fetchProgress}>Apply</button>
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
