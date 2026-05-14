CREATE TABLE notes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    personal_feeling INT NOT NULL,
    is_public BOOLEAN NOT NULL,
    creator_id BIGINT NOT NULL,
    habit_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_notes_creator FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_notes_habit FOREIGN KEY (habit_id) REFERENCES habits(id) ON DELETE CASCADE
);