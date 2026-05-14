import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect, vi, afterEach } from "vitest";
import ProgressPage from "../ProgressPage";
import HabitProgressAPI from "../../apis/HabitProgressAPI";

vi.mock("../../apis/HabitProgressAPI", () => ({
  default: {
    getProgressByUser: vi.fn(),
  },
}));

describe("ProgressPage", () => {
  afterEach(() => {
    vi.clearAllMocks();
  });

  it("shows loading initially", () => {
    HabitProgressAPI.getProgressByUser.mockReturnValue(new Promise(() => {})); // never resolves
    render(<ProgressPage />);
    expect(screen.getByText(/loading/i));
  });

  it("renders progress after data loads", async () => {
    HabitProgressAPI.getProgressByUser.mockResolvedValue([
      { date: "2026-01-01", habit: { name: "Running" }, streakValue: 1 },
      { date: "2026-01-02", habit: { name: "Running" }, streakValue: 1 },
    ]);

    render(<ProgressPage />);

    await waitFor(() => {
      expect(screen.getByText("Your Progress"));
      expect(screen.getByText("Running"));
    });
  });

  it("shows error on API failure", async () => {
    HabitProgressAPI.getProgressByUser.mockRejectedValue(new Error());

    render(<ProgressPage />);

    await waitFor(() => {
      expect(screen.getByText(/failed to load progress/i));
    });
  });
});
