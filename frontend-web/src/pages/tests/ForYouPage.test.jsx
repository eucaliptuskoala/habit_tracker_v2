import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";
import ForYouPage from "../ForYouPage";
import NoteAPI from "../../apis/NoteAPI";

vi.mock("../../apis/NoteAPI");

const mockNotes = [
  { id: 1, title: "Note 1", content: "Hello", personalFeeling: 3 },
  { id: 2, title: "Note 2", content: "World", personalFeeling: 4 },
];

describe("ForYouPage", () => {
  it("shows empty state when no notes", async () => {
    NoteAPI.getForYouContent.mockResolvedValue([]);

    render(<ForYouPage />);

    await waitFor(() => {
      screen.getByText(/no recommendations yet/i);
    });
  });

  it("renders first recommended note", async () => {
    NoteAPI.getForYouContent.mockResolvedValue(mockNotes);

    render(<ForYouPage />);

    await waitFor(() => {
      const noteElements = screen.getAllByText("Note 1");
      expect(noteElements.length).toBeGreaterThan(0);
    });
  });

  it("navigates to next note", async () => {
    NoteAPI.getForYouContent.mockResolvedValue(mockNotes);

    render(<ForYouPage />);

    await waitFor(() => screen.getAllByText("Note 1"));

    const nextButtons = screen.getAllByText("↓");
    const nextButton = nextButtons.find((btn) => !btn.disabled);

    if (!nextButton) throw new Error("No enabled 'next' button found");

    fireEvent.click(nextButton);

    await waitFor(() => {
        const notes = screen.getAllByText("Note 2");
        expect(notes.length).toBeGreaterThan(0);
    });
});
});
