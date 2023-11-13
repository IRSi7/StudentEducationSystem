package space.irsi7.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import space.irsi7.models.Mark;
import space.irsi7.models.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MarkMapper implements RowMapper<Mark> {

    @Override
    public Mark mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mark(
                rs.getInt("student_id"),
                rs.getInt("test_id"),
                rs.getInt("mark")
        );
    }
}
