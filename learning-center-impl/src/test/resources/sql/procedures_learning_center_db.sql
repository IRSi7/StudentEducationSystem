-- Скрипт для создания связей между курсами и темами

-- PROCEDURE: public.add_lnk_course_theme(integer, integer)
-- DROP PROCEDURE IF EXISTS public.add_lnk_course_theme(integer, integer);

CREATE OR REPLACE PROCEDURE public.add_lnk_course_theme(
	IN target_course integer,
	IN target_theme integer)
LANGUAGE 'sql'
AS $BODY$
INSERT INTO lnk_courses_themes VALUES(
	target_course,
	target_theme,
	(SELECT COUNT(*) FROM lnk_courses_themes
		WHERE course_id = target_course) + 1
);
$BODY$;
ALTER PROCEDURE public.add_lnk_course_theme(integer, integer)
    OWNER TO postgres;

-- Скрипт для создания связей между тестами и вопросами

-- PROCEDURE: public.add_lnk_test_question(integer, tsvector)
-- DROP PROCEDURE IF EXISTS public.add_lnk_test_question(integer, tsvector);

CREATE OR REPLACE PROCEDURE public.add_lnk_test_question(
	IN new_test_id integer,
	IN new_question_body tsvector)
LANGUAGE 'sql'
AS $BODY$
INSERT INTO lnk_tests_questions VALUES(
	new_test_id,
	new_question_body,
	(SELECT count(*) FROM lnk_tests_questions WHERE test_id = new_test_id) + 1 
);
$BODY$;
ALTER PROCEDURE public.add_lnk_test_question(integer, tsvector)
    OWNER TO postgres;