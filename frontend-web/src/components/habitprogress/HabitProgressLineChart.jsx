import React from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid
} from "recharts";

function HabitProgressLineChart({ data }) {
  const chartData = data.map(p => ({
    date: p.date,
    streak: p.streakValue
  }));

  return (
    <div className="overflow-x-auto">
      <LineChart
        width={800}
        height={300}
        data={chartData}
        margin={{ top: 20, right: 30, left: 0, bottom: 20 }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="date" />
        <YAxis />
        <Tooltip />
        <Line
          type="monotone"
          dataKey="streak"
          stroke="#FEC89A"
          strokeWidth={3}
          dot={{ r: 4 }}
        />
      </LineChart>
    </div>
  );
}

export default HabitProgressLineChart;
