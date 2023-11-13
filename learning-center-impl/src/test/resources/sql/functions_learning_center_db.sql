-- 1) Функция для получения оставшихся часов обучения студента
-- FUNCTION: public.time_left(integer)
-- DROP FUNCTION IF EXISTS public.time_left(integer);

CREATE OR REPLACE FUNCTION public.time_left(
	target_student integer)
    RETURNS integer
    LANGUAGE 'sql'
    COST 100
    VOLATILE PARALLEL UNSAFE

RETURN (SELECT sum(themes.hours) AS sum FROM ((students JOIN lnk_courses_themes lnk ON ((lnk.course_id = students.course_id))) JOIN themes ON ((lnk.theme_id = themes.id))) WHERE ((students.id = time_left.target_student) AND (NOT (lnk.theme_id IN (SELECT tests.theme_id FROM (marks JOIN tests ON ((marks.test_id = tests.id))) WHERE (marks.student_id = time_left.target_student))))));

ALTER FUNCTION public.time_left(integer)
    OWNER TO postgres;

-- 2) Функция для получения среднего балла выбранного студента
-- FUNCTION: public.gpa(integer)
-- DROP FUNCTION IF EXISTS public.gpa(integer);

CREATE OR REPLACE FUNCTION public.gpa(
	target_student integer)
    RETURNS integer
    LANGUAGE 'sql'
    COST 100
    VOLATILE PARALLEL UNSAFE

RETURN (SELECT avg(marks.mark) AS avg FROM marks WHERE (marks.student_id = gpa.target_student));

ALTER FUNCTION public.gpa(integer)
    OWNER TO postgres;