CREATE TABLE `habits_progress` (
    id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    habit_id BIGINT NOT NULL,
    date DATE NOT NULL,
    streak_value INT NOT NULL,
    created_at DATETIME NOT NULL,
    CONSTRAINT fk_habits_progress_habits FOREIGN KEY (habit_id) REFERENCES habits(id),
    CONSTRAINT uq_habit_date UNIQUE (habit_id, date)
);
