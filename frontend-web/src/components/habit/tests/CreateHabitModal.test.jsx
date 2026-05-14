import React from "react";
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import {describe, it, expect, vi, afterEach} from 'vitest';
import '@testing-library/jest-dom/vitest';
import CreateHabitModal from "../CreateHabitModal";
import * as HabitStrategyModule from "../HabitStrategySelector";

describe("CreateHabitModal Component", () => {
    afterEach(cleanup);

    it("opens and closes modal on button clicks", () => {
        render(
            <CreateHabitModal
            habitTemplates={[]}
            popularHabitTemplates={[]}
            handleCreateHabit={vi.fn()}
            />
        );

        fireEvent.click(screen.getByText("+ New Habit"));

        expect(
            screen.getByRole("heading", { name: /create a new habit/i })
        ).toBeInTheDocument();

        fireEvent.click(screen.getByRole("button", { name: "✕" }));

        expect(
            screen.queryByRole("heading", { name: /create a new habit/i })
        ).not.toBeInTheDocument();
    });

    it("calls handleCreateHabit on template submission", () => {
        const mockCreateHabit = vi.fn();
        const submitData = { name: "New Habit", description: "A new habit" };
        
         vi.spyOn(HabitStrategyModule, 'default').mockImplementation(({ onSubmit }) => {
            return <button onClick={() => onSubmit(submitData)}>Submit Habit</button>;
        });
        
        render(<CreateHabitModal 
            habitTemplates={[]} 
            popularHabitTemplates={[]} 
            handleCreateHabit={mockCreateHabit} 
        />);

        const openBtn = screen.getByText("+ New Habit");
        fireEvent.click(openBtn);

        const submitBtn = screen.getByText("Submit Habit");
        fireEvent.click(submitBtn);
        
        expect(mockCreateHabit).toHaveBeenCalledWith(submitData);
    });

});