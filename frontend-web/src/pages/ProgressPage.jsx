import React, { useEffect, useState } from "react";
import HabitProgressAPI from "../apis/HabitProgressAPI";
import HabitProgressLineChart from "../components/habitprogress/HabitProgressLineChart";
import UserActivityCalendar from "../components/habitprogress/UserActivityCalendar";

function ProgressPage() {
    const [progress, setProgress] = useState([]);
    const [progressPerHabit, setProgressPerHabit] = useState({});
    const [contribution, setContribution] = useState({});
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        HabitProgressAPI.getProgressByUser()
        .then(data => {
                setProgress(data);

                const dates = data.map(p => p.date).sort();
                const startDate = dates[0];
                const endDate = dates[dates.length - 1];

                setStartDate(startDate);
                setEndDate(endDate);

                const perHabits = {};
                data.forEach(p => {
                    if (!perHabits[p.habit.name]) {
                    perHabits[p.habit.name] = [];
                    }
                    perHabits[p.habit.name].push(p);
                });
                Object.values(perHabits).forEach(habitProgress => {
                    habitProgress.sort((a, b) => new Date(a.date) - new Date(b.date));
                });
                setProgressPerHabit(perHabits);

                const activity = {};
                data.forEach(p => {
                    if(p.streakValue == null) return;
                    activity[p.date] = (activity[p.date] || 0) + 1;
                });
                const calendar = Object.entries(activity).map(([day, value]) => ({
                    day,
                    value,
                }));
                setContribution(calendar);
        }).catch(() => setError("Failed to load progress"));
    }, []);

    if (error) {
        return <div className="p-6 text-red-600">{error}</div>;
    }

    if (!progress.length) {
        return <div className="p-6 text-gray-500">Loading...</div>;
    }

    console.log("ProgressPerHabit:", progressPerHabit);
    console.log("Contribution data:", contribution);


    return (
        <div className="max-w-6xl mx-auto p-6 space-y-10">
        <header>
            <h1 className="text-3xl font-bold">Your Progress</h1>
            <p className="text-gray-500 mt-1">
            Track how your habits evolve over time
            </p>
        </header>
            <div className="bg-white rounded-xl shadow-sm border p-6 mb-8">
                <h2 className="text-xl font-semibold mb-4">
                    Overall Activity
                </h2>
                <UserActivityCalendar data={contribution} startDate={startDate} endDate={endDate} />
            </div>

        <div className="grid gap-8">
            {Object.entries(progressPerHabit).map(([name, habitProgress]) => (
            <div
                key={name}
                className="bg-white/60 backdrop-blur rounded-2xl border shadow-sm p-6"
            >
                <div className="flex items-center justify-between mb-6">
                    <h2 className="text-xl font-semibold">{name}</h2>
                    <span className="text-sm text-gray-500">
                        {habitProgress.length} days tracked
                    </span>
                </div>
                <div className="h-[320px]">
                    <HabitProgressLineChart data={habitProgress} />
                </div>
            </div>
            ))}
            </div>
        </div>
    );
}

export default ProgressPage;
