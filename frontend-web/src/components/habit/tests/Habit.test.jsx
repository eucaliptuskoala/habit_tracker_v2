import React from "react";
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import Habit from "../Habit";
import {describe, it, expect, vi, afterEach} from 'vitest';
import '@testing-library/jest-dom/vitest';

afterEach(cleanup);

describe("Habit Component", () => {

    it("renders habit details correctly", () => {

        render(<Habit 
            habit={{id: 1, name: "Test Habit", description: "This is a test habit", streak: 5, lastUpdatedStreak: "2024-06-20T00:00:00Z"}} 
            handleUpdate={vi.fn()} 
            handleDelete={vi.fn()} 
        />);

        expect(screen.getByText("Test Habit")).toBeInTheDocument();
    })

    it("renders habit, update is available", () => {

        render(<Habit 
            habit={{id: 1, name: "Test Habit", description: "This is a test habit", streak: 5, lastUpdatedStreak: "2024-06-19T00:00:00Z"}} 
            handleUpdate={vi.fn()} 
            handleDelete={vi.fn()} 
        />);

        const updateBtn = screen.getByRole("button", {
        name: /mark as done/i,
        });

        expect(updateBtn).toBeEnabled();
    });

    it("calls update handler on button click", () => {

        const mockUpdate = vi.fn();

        render(<Habit 
            habit={{id: 1, name: "Test Habit", description: "This is a test habit", streak: 5, lastUpdatedStreak: "2024-06-19T00:00:00Z"}} 
            handleUpdate={mockUpdate} 
            handleDelete={vi.fn()} 
        />);
        
        const updateBtn = screen.getByRole("button", {
            name: /mark as done/i,
        });
        expect(updateBtn).toBeInTheDocument();
        expect(updateBtn).not.toBeDisabled();

        fireEvent.click(updateBtn);
        expect(mockUpdate).toHaveBeenCalledWith(1);
    });

    it("renders habit, update is disabled", () => {

        render(<Habit 
            habit={{id: 1, name: "Test Habit", description: "This is a test habit", streak: 5, lastUpdatedStreak: new Date().toISOString()}} 
            handleUpdate={vi.fn()} 
            handleDelete={vi.fn()} 
        />);

        const updateBtn = screen.getByRole("button", {
            name: /already updated today/i,
        });

        expect(updateBtn).toBeDisabled();
    });

    it("renders and opens menu, calls delete handler", () => {

        const mockDelete = vi.fn();

        render(<Habit 
            habit={{id: 1, name: "Test Habit", description: "This is a test habit", streak: 5, lastUpdatedStreak: "2024-06-19T00:00:00Z"}} 
            handleUpdate={vi.fn()} 
            handleDelete={mockDelete} 
        />);

        const menuBtn = screen.getByRole("button", { name: "⋮" });
        fireEvent.click(menuBtn);

        const deleteBtn = screen.getByText(/delete habit/i);
        fireEvent.click(deleteBtn);

        expect(mockDelete).toHaveBeenCalledWith(1);

        fireEvent.click(deleteBtn);
        expect(mockDelete).toHaveBeenCalledWith(1);
    });
});