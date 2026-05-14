-- USERS (15 total)
INSERT INTO users (id, name, email, password, is_admin) VALUES
                                                            (1,'Alice','alice@test.com','{noop}password',0),
                                                            (2,'Bob','bob@test.com','{noop}password',0),
                                                            (3,'Charlie','charlie@test.com','{noop}password',0),
                                                            (4,'Diana','diana@test.com','{noop}password',0),
                                                            (5,'Evan','evan@test.com','{noop}password',0),
                                                            (6,'Fiona','fiona@test.com','{noop}password',0),
                                                            (7,'George','george@test.com','{noop}password',0),
                                                            (8,'Hannah','hannah@test.com','{noop}password',0),
                                                            (9,'Ivan','ivan@test.com','{noop}password',0),
                                                            (10,'Julia','julia@test.com','{noop}password',0),
                                                            (11,'Kevin','kevin@test.com','{noop}password',0),
                                                            (12,'Laura','laura@test.com','{noop}password',0),
                                                            (13,'Mark','mark@test.com','{noop}password',0),
                                                            (14,'Nina','nina@test.com','{noop}password',0),
                                                            (15,'Admin','admin@test.com','{noop}password',1);


-- HABIT TEMPLATES
INSERT INTO habit_templates (id, name, popularity) VALUES
                                                       (1,'Drink Water',180),
                                                       (2,'Read Book',140),
                                                       (3,'Workout',200),
                                                       (4,'Meditation',160),
                                                       (5,'Sleep Early',90),
                                                       (6,'No Sugar',75),
                                                       (7,'Learn Coding',220),
                                                       (8,'Journal',130),
                                                       (9,'Morning Walk',110),
                                                       (10,'Stretching',60),
                                                       (11,'Language Learning',150),
                                                       (12,'Yoga',95),
                                                       (13,'Cold Shower',50),
                                                       (14,'Healthy Breakfast',100),
                                                       (15,'Pomodoro Focus',160);


-- HABITS (≈30, mix of different templates)
INSERT INTO habits (id, name, description, streak, last_updated_streak, threshold_days, habit_template_id, creator_id) VALUES
                                                                                                                           (1,'Drink Water','At least 2L daily',10,'2026-01-07',7,1,1),
                                                                                                                           (2,'Workout','Strength training',4,'2026-01-05',5,3,1),
                                                                                                                           (3,'Learn Coding','Solve problems on LeetCode',14,'2026-01-07',7,7,1),
                                                                                                                           (4,'Read Book','Read 20 pages',5,'2026-01-06',7,2,2),
                                                                                                                           (5,'Meditation','10 minutes',8,'2026-01-06',5,4,2),
                                                                                                                           (6,'Sleep Early','In bed by 11PM',6,'2026-01-07',7,5,3),
                                                                                                                           (7,'Morning Walk','Early 5k walk',9,'2026-01-07',5,9,4),
                                                                                                                           (8,'Pomodoro Focus','Focus sessions',12,'2026-01-07',7,15,5),
                                                                                                                           (9,'Yoga','Light flexibility',3,'2025-12-29',5,12,6),
                                                                                                                           (10,'No Sugar','Avoid candies/desserts',5,'2026-01-04',10,6,7),
                                                                                                                           (11,'Language Learning','Duolingo - Spanish',4,'2026-01-06',5,11,8),
                                                                                                                           (12,'Journal','Write daily reflections',11,'2026-01-06',7,8,9),
                                                                                                                           (13,'Healthy Breakfast','Smoothies or oats',8,'2026-01-07',7,14,10),
                                                                                                                           (14,'Stretching','After work routine',2,'2025-12-28',5,10,11),
                                                                                                                           (15,'Cold Shower','Start strong',3,'2026-01-05',3,13,12),
                                                                                                                           (16,'Evening Reflection','Gratitude notes',5,'2026-01-06',7,8,13),
                                                                                                                           (17,'Step Count 10k','Walk a full 10k steps',7,'2026-01-06',5,9,14),
                                                                                                                           (18,'Drink Water','Stay hydrated',2,'2026-01-03',7,1,2),
                                                                                                                           (19,'Workout','Cardio style',6,'2026-01-06',5,3,3),
                                                                                                                           (20,'Coding Challenge','1 coding task daily',12,'2026-01-07',5,7,4),
                                                                                                                           (21,'Pomodoro Focus','Session tracking',7,'2026-01-05',7,15,7),
                                                                                                                           (22,'Yoga Breathing','Calm mind sessions',3,'2026-01-04',5,12,10),
                                                                                                                           (23,'Read Book','Finish one per month',9,'2026-01-07',10,2,11),
                                                                                                                           (24,'Journal','Affirmations + progress',8,'2026-01-05',7,8,14),
                                                                                                                           (25,'Sleep Early','Before midnight',4,'2026-01-06',7,5,9),
                                                                                                                           (26,'Daily Coding','Personal projects',10,'2026-01-07',5,7,5),
                                                                                                                           (27,'Morning Run','Workout outdoors',6,'2026-01-07',5,9,6),
                                                                                                                           (28,'Drink Water','2L target',5,'2026-01-07',7,1,3),
                                                                                                                           (29,'Stretching','Office breaks',4,'2026-01-07',5,10,8),
                                                                                                                           (30,'Read Book','Tech/Non-fiction',10,'2026-01-07',7,2,13);


-- NOTES (~20 entries covering different habits)
INSERT INTO notes (id, title, content, personal_feeling, is_public, creator_id, habit_id, created_at, updated_at) VALUES
                                                                                                                      (21,'Hydration goal','Finally hit 2.5L today, small win!',9,true,1,1,'2026-01-02','2026-01-02'),
                                                                                                                      (22,'Sluggish workout','Felt lazy but still pushed through',6,false,1,2,'2026-01-03','2026-01-03'),
                                                                                                                      (23,'Late night debugging','Lost track of time coding again',7,true,1,3,'2026-01-06','2026-01-06'),
                                                                                                                      (24,'Relaxed mind','Longest meditation streak so far',10,true,2,5,'2026-01-05','2026-01-05'),
                                                                                                                      (25,'Midweek slump','Couldn’t focus during meditation',3,false,2,5,'2026-01-07','2026-01-07'),
                                                                                                                      (26,'Productive reading','Finished half the book!',9,true,2,4,'2026-01-02','2026-01-02'),
                                                                                                                      (27,'Fell asleep early','Actually managed before 11PM!',8,true,3,6,'2026-01-06','2026-01-06'),
                                                                                                                      (28,'Morning lag','Skipped walk, too cold outside',4,false,4,7,'2026-01-03','2026-01-03'),
                                                                                                                      (29,'Focus bursts','Pomodoros helped me avoid distractions',9,true,5,8,'2026-01-04','2026-01-04'),
                                                                                                                      (30,'Sugar-free day','Avoided desserts again today',10,true,7,10,'2026-01-02','2026-01-02'),
                                                                                                                      (31,'Evening calm','Gentle yoga after work felt great',9,true,6,9,'2026-01-06','2026-01-06'),
                                                                                                                      (32,'Skipped breakfast','Felt the difference, won’t repeat',5,false,10,13,'2026-01-05','2026-01-05'),
                                                                                                                      (33,'Reflections','Wrote about motivation dips',7,false,9,12,'2026-01-05','2026-01-05'),
                                                                                                                      (34,'Clean day','No caffeine or sugar!',8,true,7,10,'2026-01-05','2026-01-05'),
                                                                                                                      (35,'Coding block','Couldn’t focus on tasks today',3,false,4,20,'2026-01-03','2026-01-03'),
                                                                                                                      (36,'Quick jog','Short but refreshing morning run',8,true,6,27,'2026-01-04','2026-01-04'),
                                                                                                                      (37,'Rekindled habit','Picked up journaling again!',9,true,14,24,'2026-01-06','2026-01-06'),
                                                                                                                      (38,'Motivated start','Drank 1L right after waking up',9,true,3,28,'2026-01-06','2026-01-06'),
                                                                                                                      (39,'Steady coding pace','No overthinking – just progress',10,true,5,26,'2026-01-06','2026-01-06'),
                                                                                                                      (40,'Better focus','Pomodoro method still rocks',9,true,7,21,'2026-01-07','2026-01-07'),
                                                                                                                      (41,'Tough restart','Skipped reading all weekend',4,false,11,23,'2026-01-07','2026-01-07'),
                                                                                                                      (42,'Fewer distractions','Turned off phone during sessions',8,true,5,8,'2026-01-07','2026-01-07'),
                                                                                                                      (43,'Calm breathing','Loved the session this morning',9,true,10,22,'2026-01-07','2026-01-07'),
                                                                                                                      (44,'Easy morning stretch','Productivity boost after stretching',8,true,8,29,'2026-01-07','2026-01-07'),
                                                                                                                      (45,'Journaling helps','Writing seems to reset my mood',9,false,9,12,'2026-01-07','2026-01-07'),
                                                                                                                      (46,'Restart #2','Another small win – back to coding daily',8,true,5,26,'2026-01-07','2026-01-07'),
                                                                                                                      (47,'Missed goal','Didn’t hit 10k steps today',6,false,14,17,'2026-01-07','2026-01-07'),
                                                                                                                      (48,'Group run','Joined friends, made it fun again',10,true,6,27,'2026-01-07','2026-01-07'),
                                                                                                                      (49,'Consistency pays off','Week 2 without missing a single drink day!',10,true,3,28,'2026-01-07','2026-01-07'),
                                                                                                                      (50,'Clear mind','Meditation really improves focus',10,true,2,5,'2026-01-08','2026-01-08');

-- HABITS_PROGRESS
-- Intentionally includes date gaps larger than threshold_days to emulate resets
INSERT INTO habits_progress (habit_id, date, streak_value, created_at) VALUES
-- Habit 1 (Drink Water): regular but small gap resets
(1,'2025-12-01',1,'2025-12-01'),
(1,'2025-12-02',2,'2025-12-02'),
(1,'2025-12-03',3,'2025-12-03'),
(1,'2025-12-07',1,'2025-12-07'), -- gap > threshold (reset streak)
(1,'2025-12-08',2,'2025-12-08'),
(1,'2025-12-10',3,'2025-12-10'),
(1,'2025-12-15',1,'2025-12-15'),

-- Habit 3 (Learn Coding): several restarts
(3,'2025-12-01',1,'2025-12-01'),
(3,'2025-12-02',2,'2025-12-02'),
(3,'2025-12-05',3,'2025-12-05'),
(3,'2025-12-20',1,'2025-12-20'), -- restart after long break
(3,'2025-12-21',2,'2025-12-21'),
(3,'2025-12-23',3,'2025-12-23'),
(3,'2026-01-02',1,'2026-01-02'),
(3,'2026-01-03',2,'2026-01-03'),
(3,'2026-01-06',5,'2026-01-06'),

-- Habit 5 (Meditation): only a few entries, one long break
(5,'2025-12-10',1,'2025-12-10'),
(5,'2025-12-11',2,'2025-12-11'),
(5,'2025-12-17',1,'2025-12-17'),
(5,'2025-12-18',2,'2025-12-18'),

-- Habit 8 (Pomodoro Focus): consistent, minor gaps
(8,'2025-12-26',1,'2025-12-26'),
(8,'2025-12-27',2,'2025-12-27'),
(8,'2025-12-28',3,'2025-12-28'),
(8,'2025-12-30',4,'2025-12-30'),
(8,'2026-01-02',5,'2026-01-02'),
(8,'2026-01-04',6,'2026-01-04'),
(8,'2026-01-07',7,'2026-01-07'),

-- Habit 20 (Coding Challenge): good test for steady → missing → restart
(20,'2025-12-25',1,'2025-12-25'),
(20,'2025-12-26',2,'2025-12-26'),
(20,'2025-12-27',3,'2025-12-27'),
(20,'2025-12-30',4,'2025-12-30'),
(20,'2026-01-03',1,'2026-01-03'),
(20,'2026-01-04',2,'2026-01-04'),
(20,'2026-01-06',3,'2026-01-06');
