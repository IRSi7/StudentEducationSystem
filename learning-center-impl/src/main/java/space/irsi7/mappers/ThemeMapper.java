package space.irsi7.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ThemeMapper implements RowMapper<Theme> {

    @Override
    public Theme mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Theme(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("hours")
        );
    }
}
