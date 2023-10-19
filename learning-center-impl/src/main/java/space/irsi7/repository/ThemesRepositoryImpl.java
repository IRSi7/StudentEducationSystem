package space.irsi7.repository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import space.irsi7.dao.YamlDaoImpl;
import space.irsi7.enums.PathsEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.interfaces.Repositories.ThemesRepository;
import space.irsi7.models.Theme;
import space.irsi7.services.StudentServiceImpl;

import java.io.InputStream;
import java.util.Map;

@Repository
public class ThemesRepositoryImpl implements ThemesRepository {

    @Autowired
    private YamlDaoImpl yamlDao;
    private Map<Integer, Theme> themeMap;
//    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Theme getTheme(int id) {
        return themeMap.get(id);
    }

    @PostConstruct
    public void init(){
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(PathsEnum.CONFIG.getPath());

            themeMap = yamlDao.readYamlThemes(stream);
//            logger.info("Данные о темах успешно считаны из config.yaml");
        } catch (Exception e) {
//            logger.error("Ошибка при чтении тем из config.yaml");
            throw new IllegalInitialDataException(e);
        }
    }
}
