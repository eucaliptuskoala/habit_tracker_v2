import React from "react";
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import {describe, it, expect, vi, afterEach} from 'vitest';
import '@testing-library/jest-dom/vitest';
import HabitTemplateCard from "../../template/HabitTemplateCard";

describe("HabitTemplateCard Component", () => {

    afterEach(cleanup);

    it("renders template details correctly", () => {
        const template = { name: "Test Template", popularity: 42 };

        render(<HabitTemplateCard t={template} createFromTemplate={vi.fn()} />);

        expect(screen.getByText("Test Template")).toBeInTheDocument();
        expect(
            screen.getByText((content) => content.includes("42"))
        ).toBeInTheDocument();
    });

    it("calls createFromTemplate on click", () => {
        const template = { name: "Test Template", popularity: 42 };
        const mockCreate = vi.fn();

        render(<HabitTemplateCard t={template} createFromTemplate={mockCreate} />);

        const card = screen.getByText("Test Template");
        
        fireEvent.click(card);
        expect(mockCreate).toHaveBeenCalledWith(template);
    });

});