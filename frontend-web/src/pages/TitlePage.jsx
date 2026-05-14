import { Link } from "react-router-dom";
import CreateHabits from "../assets/create-habits.svg?react";
import PersonalData from "../assets/personal-data.svg?react";
import SortingThoughts from "../assets/sorting-thoughts.svg?react";
import TakingNotes from "../assets/taking-notes.svg?react";

const features = [
  {
    title: "Track Daily",
    description:
      "Monitor your daily habits with ease and visualize your consistency over time. See streaks, completed habits, and progress at a glance to stay on top of your routines every day.",
    svg: CreateHabits,
  },
  {
    title: "Reflect & Improve",
    description:
      "Add notes, reflect on your actions, and identify patterns to improve yourself. Organize your thoughts, track your mood, and gain insights from your daily reflections to grow steadily.",
    svg: TakingNotes,
  },
  {
    title: "Watch Your Growth",
    description:
      "Visualize your progress with detailed charts and statistics. Understand your habits better through data-driven insights, helping you make informed decisions to enhance your personal development journey.",
    svg: PersonalData,
  },
  {
    title: "Stay Inspired",
    description:
      "Discover new ideas and motivation on the For You Page to reach your goals. Find tips, success stories, and habit inspiration to keep your journey exciting and rewarding.",
    svg: SortingThoughts,
  },
];

function TitlePage() {
  return (
    <section className="min-h-screen flex flex-col items-center text-black px-6 py-16">
      <div className="max-w-3xl text-center mb-16">
        <h1 className="text-6xl md:text-7xl font-extrabold mb-4 tracking-wide">
          Habit Tracker
        </h1>

        <p className="text-lg md:text-xl text-gray-800 leading-relaxed">
          Build habits that last. Track your progress, reflect on your growth, 
          and become the best version of yourself — one step at a time.
        </p>
      </div>

      <div className="space-y-20 w-full max-w-6xl">
        {features.map((feature, index) => {
          const Svg = feature.svg;
          const isEven = index % 2 === 1;

          return (
            <div
              key={feature.title}
              className={`flex flex-col md:flex-row items-center gap-12 ${
                isEven ? "md:flex-row-reverse" : ""
              }`}
            >
              <div className="flex-1 flex justify-center md:justify-end">
                <Svg className="w-64 h-64 md:w-80 md:h-80 animate-fadeIn" />
              </div>

              <div className="flex-1 bg-[var(--color-2)] p-10 rounded-2xl shadow-lg transition-transform hover:-translate-y-2 hover:shadow-2xl">
                <h3 className="font-semibold text-2xl mb-4">{feature.title}</h3>
                <p className="text-gray-700 text-base leading-relaxed">{feature.description}</p>
              </div>
            </div>
          );
        })}
      </div>

      <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mt-20">
        <Link
          to="/sign-up"
          className="px-12 py-4 rounded-xl bg-[var(--color-9)] text-black text-lg font-semibold hover:bg-[var(--color-6)] hover:text-white transition-transform hover:scale-105 shadow-md"
        >
          Get Started
        </Link>
      </div>

      <p className="mt-12 text-sm text-gray-600 text-center">
        Designed to make consistency effortless ✦
      </p>
    </section>
  );
}

export default TitlePage;