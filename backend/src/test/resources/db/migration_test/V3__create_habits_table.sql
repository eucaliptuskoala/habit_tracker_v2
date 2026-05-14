CREATE TABLE `habits` (
    id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(20) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    streak INT NOT NULL,
    last_updated_streak DATETIME NULL,
    threshold_days INT NOT NULL,
    habit_template_id BIGINT NULL,
    creator_id BIGINT NOT NULL,
    CONSTRAINT fk_habits_creator FOREIGN KEY (creator_id) REFERENCES users(id),
    CONSTRAINT fk_habits_template FOREIGN KEY (habit_template_id) REFERENCES habit_templates(id)
);