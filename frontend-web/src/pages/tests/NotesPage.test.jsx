import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect, vi, afterEach } from "vitest";
import NotesPage from "../NotesPage";
import NoteAPI from "../../apis/NoteAPI";
import HabitAPI from "../../apis/HabitAPI";

vi.mock("../../apis/NoteAPI");
vi.mock("../../apis/HabitAPI");

describe("NotesPage", () => {
  afterEach(() => {
    vi.clearAllMocks();
  });

  it("shows empty message when no notes", async () => {
    NoteAPI.getNotes.mockResolvedValue([]);
    HabitAPI.getHabitsByUser.mockResolvedValue([]);

    render(<NotesPage />);

    await waitFor(() => {
      const el = screen.getByText(/no notes yet/i);
      expect(el).not.toBeNull();
    });
  });

  it("renders notes when available", async () => {
    NoteAPI.getNotes.mockResolvedValue([
      { id: 1, title: "Test Note", content: "Test", personalFeeling: 2 },
    ]);
    HabitAPI.getHabitsByUser.mockResolvedValue([]);

    render(<NotesPage />);

    await waitFor(() => {
      const el = screen.getByText("Test Note");
      expect(el).not.toBeNull();
    });
  });

  it("shows error if API fails", async () => {
    NoteAPI.getNotes.mockRejectedValue(new Error());
    HabitAPI.getHabitsByUser.mockResolvedValue([]);

    render(<NotesPage />);

    await waitFor(() => {
      const el = screen.getByText(/failed to fetch notes/i);
      expect(el).not.toBeNull();
    });
  });
});
