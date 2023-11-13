--1) Инициация таблицы с менторами
INSERT INTO mentors (name, grade) VALUES
                                      ('Test Mentor 1', 'Test Senior'),
                                      ('Test Mentor 2', 'Test TeamLead');

--2) Инициация таблицы с группами
INSERT INTO bands (project, mentor_id) VALUES
                                           ('Test Project 1', 1),
                                           ('Test Project 2', 2);

--3) Инициация таблицы со курсами
INSERT INTO courses (description) VALUES
                                      ('Test Course 1'),
                                      ('Test Course 2');

--4) Инициация таблицы со студентами
INSERT INTO students (name, course_id, band_id) VALUES
                                                    ('Test Student 1', 1, 1),
                                                    ('Test Student 2', 2, 2);

--5) Инициация таблицы с темами
INSERT INTO themes (name, hours) VALUES
                                     ('Test Theme 1', 1),
                                     ('Test Theme 2', 3);

--6) Инициация таблицы связующей курсы и темы
INSERT INTO lnk_courses_themes VALUES (1, 1, 1);
INSERT INTO lnk_courses_themes VALUES (1, 2, 2);

--7) Инициация таблицы с тестами
INSERT INTO tests (theme_id, duration) VALUES
                                           (1, 1),
                                           (2, 2);

--8) Инициация таблицы с вопросами
INSERT INTO questions VALUES
                          ('Test Question 1', 1),
                          ('Test Question 2', 2);

--9) Инициация таблицы связующей курсы и темы
INSERT INTO lnk_tests_questions VALUES (1, 'Test Question 1', 1);
INSERT INTO lnk_tests_questions VALUES (2, 'Test Question 2', 2);

--10) Инициация таблицы с оценками
INSERT INTO marks VALUES
                      (1, 1, 100),
                      (2, 2, 0);