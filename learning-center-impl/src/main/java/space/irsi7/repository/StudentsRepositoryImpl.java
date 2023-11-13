package space.irsi7.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.irsi7.exceptions.StudentNotFoundException;
import space.irsi7.interfaces.repositories.StudentsRepository;
import space.irsi7.mappers.StudentMapper;
import space.irsi7.models.Student;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Repository
public class StudentsRepositoryImpl implements StudentsRepository {
    private static final Logger logger = LoggerFactory.getLogger(StudentsRepositoryImpl.class);
    private final JdbcTemplate mJdbcTemplate;

    private final StudentMapper studentMapper;

    //Запросы к БД
    private final static String SQL_GET_STUDENT_BY_ID =
            "SELECT * FROM students WHERE id = ?;";
    private final static String SQL_DELETE_STUDENT_BY_ID =
            "DELETE FROM students WHERE id = ?;";
    private final static String SQL_PUT_STUDENT =
            "INSERT INTO students (name, course_id, band_id) VALUES (?,?,?);";

    private final static String SQL_GET_ALL_STUDENTS =
            "SELECT * FROM students";

    @Autowired
    public StudentsRepositoryImpl(DataSource dataSource, StudentMapper mapper){
        this.mJdbcTemplate = new JdbcTemplate(dataSource);
        this.studentMapper = mapper;
    }

    @Override
    public Student getStudentById(int id) throws StudentNotFoundException {
        try {
             return mJdbcTemplate.queryForObject(
                    SQL_GET_STUDENT_BY_ID,
                    new Object[]{id},
                    new int[]{Types.INTEGER},
                    studentMapper
            );
        } catch (DataAccessException e){
            throw new StudentNotFoundException(e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return mJdbcTemplate.query(
                SQL_GET_ALL_STUDENTS,
                studentMapper
        );
    }

    @Override
    public boolean addStudent(String name, int course, int group) {
        try{
            mJdbcTemplate.update(
                SQL_PUT_STUDENT,
                new Object[]{name, course, group},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER}
        );
        return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean removeStudent(int id) {
        try {
            mJdbcTemplate.update(
                    SQL_DELETE_STUDENT_BY_ID,
                    new Object[]{id},
                    new int[]{Types.INTEGER}
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
