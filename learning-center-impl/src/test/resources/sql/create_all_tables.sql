-- 1) Таблица с наставниками
CREATE TABLE public.mentors
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    grade character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

-- 2) Таблица с группами студентов
CREATE TABLE public.bands
(
    id serial NOT NULL,
    project character varying(50) NOT NULL,
    mentor_id integer NOT NULL references mentors(id),
    PRIMARY KEY (id)
);

-- 3) Таблица с курсами
CREATE TABLE public.courses
(
    id serial NOT NULL,
    description text,
    PRIMARY KEY (id)
);

-- 4) Таблица с темами
CREATE TABLE public.themes
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    hours integer NOT NULL,
    PRIMARY KEY (id)
);

-- 5) Таблица связующая темы с курсами
CREATE TABLE public.lnk_courses_themes
(
    course_id integer NOT NULL references mentors(id),
    theme_id integer NOT NULL references themes(id),
    position integer NOT NULL,
    PRIMARY KEY (course_id, theme_id)
);

-- 6) Таблица со студентами
CREATE TABLE public.students
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    course_id integer references courses(id),
    band_id integer references bands(id),
    PRIMARY KEY (id)
);

-- 7) Таблица с тестами
CREATE TABLE public.tests
(
    id serial NOT NULL,
    theme_id integer references themes(id) NOT NULL,
    duration integer NOT NULL,
    PRIMARY KEY (id)
);

-- 8) Таблица с вопросами
CREATE TABLE public.questions
(
    question_body text NOT NULL,
    answer integer NOT NULL,
    PRIMARY KEY (question_body)
);

-- 9) Таблица связующая тесты с вопросами
CREATE TABLE public.lnk_tests_questions
(
    test_id integer NOT NULL references tests(id),
    question_body text NOT NULL references questions(question_body),
    position integer NOT NULL,
    PRIMARY KEY (test_id, question_body)
);

-- 10) Таблица связующая тесты с оценками за тесты
CREATE TABLE public.marks
(
    student_id integer references students(id) NOT NULL,
    test_id integer references tests(id) NOT NULL,
    mark integer NOT NULL,
    PRIMARY KEY (student_id, test_id)
);