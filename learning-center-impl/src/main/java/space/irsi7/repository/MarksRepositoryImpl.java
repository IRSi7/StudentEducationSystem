package space.irsi7.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.irsi7.interfaces.repositories.MarksRepository;
import space.irsi7.mappers.MarkMapper;
import space.irsi7.models.Mark;
import space.irsi7.services.StudentServiceImpl;

import java.sql.Types;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MarksRepositoryImpl implements MarksRepository {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final JdbcTemplate mJdbcTemplate;
    private final MarkMapper markMapper;

    //Запросы
    public static final String SQL_GETA_ALL_MARKS = "SELECT * FROM marks;";
    public static final String SQL_GET_MARKS_BY_STUDENT_ID = "SELECT * FROM marks WHERE student_id = ?;";
    public static final String SQL_GET_AMOUNT_MARKS_BY_STUDENT_ID = "SELECT count(*) FROM marks WHERE student_id = 1;";
    private final static String SQL_PUT_MARK =
            "INSERT INTO marks (student_id, test_id, mark) VALUES (?,?,?);";

    @Autowired
    public MarksRepositoryImpl(JdbcTemplate mJdbcTemplate, MarkMapper markMapper) {
        this.mJdbcTemplate = mJdbcTemplate;
        this.markMapper = markMapper;
    }

    @Override
    public List<Mark> getMarksByStudentId(int studentId) {
        return mJdbcTemplate.query(
                SQL_GET_MARKS_BY_STUDENT_ID,
                new Object[]{studentId},
                new int[]{Types.INTEGER},
                markMapper
        );
    }

    @Override
    public Integer getMarksAmountByStudentId(int studentId) {
        return mJdbcTemplate.queryForObject(
                SQL_GET_AMOUNT_MARKS_BY_STUDENT_ID,
                Integer.class
        );
    }

    @Override
    public boolean addMark(int studentId, int testId, int mark) {
        try {
            mJdbcTemplate.update(
                    SQL_PUT_MARK,
                    new Object[]{studentId, testId, mark},
                    new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER}
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Integer getGpa(int studentId) {
        List<Mark> marks = getMarksByStudentId(studentId);
        AtomicInteger answer = new AtomicInteger();
        marks.forEach(it -> answer.addAndGet(it.mark()));
        return answer.get() / marks.size();
    }

    @Override
    public List<Mark> getAllMarks() {
        return mJdbcTemplate.query(
                SQL_GETA_ALL_MARKS,
                markMapper
        );
    }
}
