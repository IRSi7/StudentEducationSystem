package space.irsi7.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import space.irsi7.models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("course_id"),
                rs.getInt("band_id")
        );
    }
}
