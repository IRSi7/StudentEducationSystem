package space.irsi7.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.irsi7.interfaces.repositories.TestsRepository;
import space.irsi7.mappers.TestMapper;
import space.irsi7.models.Test;
import space.irsi7.services.StudentServiceImpl;

import java.sql.Types;
import java.util.List;

@Repository
public class TestsRepositoryImpl implements TestsRepository {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final JdbcTemplate mJdbcTemplate;
    private final TestMapper testMapper;

    //Запросы
    public static final String SQL_GET_TEST_BY_ID = "SELECT * FROM tests WHERE id = ?;";

    public static final String SQL_GET_ALL_TESTS =
            "SELECT * FROM tests;";

    public static final String SQL_GET_TESTS_BY_STUDENT_ID =
            "SELECT id, theme_id, duration FROM tests JOIN marks ON tests.id = marks.test_id WHERE marks.student_id = ?;";


    @Autowired
    public TestsRepositoryImpl(JdbcTemplate mJdbcTemplate, TestMapper testMapper) {
        this.mJdbcTemplate = mJdbcTemplate;
        this.testMapper = testMapper;
    }

    @Override
    public Test getTestById(int id) {
        return mJdbcTemplate.queryForObject(
                SQL_GET_TEST_BY_ID,
                new Object[]{id},
                new int[]{Types.INTEGER},
                testMapper
        );
    }

    @Override
    public List<Test> getTestsByStudentId(int id) {
        return mJdbcTemplate.query(
                SQL_GET_TESTS_BY_STUDENT_ID,
                new Object[]{id},
                new int[]{Types.INTEGER},
                testMapper
        );
    }

    @Override
    public List<Test> getAllTests() {
        return mJdbcTemplate.query(
                SQL_GET_ALL_TESTS,
                testMapper
        );
    }
}
