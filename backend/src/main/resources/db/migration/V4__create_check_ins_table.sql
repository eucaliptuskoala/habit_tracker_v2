CREATE TABLE check_ins (
    id BIGSERIAL PRIMARY KEY,
    habit_id BIGINT NOT NULL REFERENCES habits(id),
    date DATE NOT NULL,
    streak_value INT NOT NULL,
    content TEXT,
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    mood VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    UNIQUE (habit_id, date)
);
