ALTER TABLE check_ins DROP CONSTRAINT check_ins_habit_id_fkey;
ALTER TABLE check_ins ADD CONSTRAINT check_ins_habit_id_fkey
    FOREIGN KEY (habit_id) REFERENCES habits(id) ON DELETE CASCADE;
