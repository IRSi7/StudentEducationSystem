package space.irsi7.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.irsi7.interfaces.repositories.ThemesRepository;
import space.irsi7.mappers.ThemeMapper;
import space.irsi7.models.Theme;
import space.irsi7.services.StudentServiceImpl;

import java.sql.Types;
import java.util.List;

@Repository
public class ThemesRepositoryImpl implements ThemesRepository {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final JdbcTemplate mJdbcTemplate;
    private final ThemeMapper themeMapper;

    //Запросы
    public static final String SQL_GET_THEME_BY_ID = "SELECT * FROM themes WHERE id = ?;";
    public static final String SQL_GET_THEMES_BY_COURSE =
            "SELECT id, name, hours FROM lnk_courses_themes " +
                    "JOIN themes ON theme_id = id " +
                    "WHERE course_id = ? ORDER BY lnk_courses_themes.position;";

    @Autowired
    public ThemesRepositoryImpl(JdbcTemplate mJdbcTemplate, ThemeMapper themeMapper) {
        this.mJdbcTemplate = mJdbcTemplate;
        this.themeMapper = themeMapper;
    }

    @Override
    public Theme getThemeById(int id) {
        return mJdbcTemplate.queryForObject(
                SQL_GET_THEME_BY_ID,
                new Object[]{id},
                new int[]{Types.INTEGER},
                themeMapper
        );
    }

    @Override
    public List<Theme> getThemesByCourseId(int courseId) {
        return mJdbcTemplate.query(
                SQL_GET_THEMES_BY_COURSE,
                new Object[]{courseId},
                new int[]{Types.INTEGER},
                themeMapper
        );
    }
}
