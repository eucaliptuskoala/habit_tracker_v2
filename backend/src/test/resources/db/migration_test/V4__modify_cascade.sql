ALTER TABLE habits DROP CONSTRAINT fk_habits_creator;


ALTER TABLE habits
    ADD CONSTRAINT fk_habits_creator
        FOREIGN KEY (creator_id) REFERENCES users(id)
            ON DELETE CASCADE;

ALTER TABLE habits DROP CONSTRAINT fk_habits_template;

ALTER TABLE habits
    ADD CONSTRAINT fk_habits_template
        FOREIGN KEY (habit_template_id) REFERENCES habit_templates(id);