package space.irsi7.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import space.irsi7.models.Student;
import space.irsi7.models.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestMapper implements RowMapper<Test> {

    @Override
    public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Test(
                rs.getInt("id"),
                rs.getInt("theme_id"),
                rs.getInt("duration")
        );
    }
}
