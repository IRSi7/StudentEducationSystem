package space.irsi7.interfaces.repositories;

import space.irsi7.models.Theme;

import java.util.List;

public interface ThemesRepository {

    Theme getThemeById(int id);
    List<Theme> getThemesByCourseId(int course_id);
}
