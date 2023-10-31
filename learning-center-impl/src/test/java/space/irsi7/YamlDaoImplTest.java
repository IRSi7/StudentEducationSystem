package space.irsi7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import space.irsi7.dao.YamlDaoImpl;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class YamlDaoImplTest {

    YamlDaoImpl yamlDao = new YamlDaoImpl();
    static Map<Integer, Student> expectedStudentsMap = new HashMap<>();
    static Map<Integer, Theme> expectedThemesMap = new HashMap<>();
    static Map<Integer, Course> expectedCoursesMap = new HashMap<>();

    @BeforeAll
    public static void init(){
        expectedStudentsMap.put(0, new Student(0, "Test Test", 0, new ArrayList<>(List.of(0, 0, 0)), 0));
        expectedThemesMap.put(0, new Theme(0, "Test", 0));
        expectedCoursesMap.put(0, new Course(0, new ArrayList<>(List.of(0, 0, 0))));
    }

    @Test
    public void dao_write_correct_student_file() {
        try {
            yamlDao.writeYAML(new ArrayList<>(expectedStudentsMap.values()), TestPathEnum.STUDENTS.getPath());
            Map<Integer, Student> readenMap = yamlDao.readYamlStudents(TestPathEnum.STUDENTS.getPath());
            assertEquals(expectedStudentsMap.get(0), readenMap.get(0));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void dao_read_correct_student_file() {
        try {
            Map<Integer, Student> readenMap = yamlDao.readYamlStudents(TestPathEnum.STUDENTS.getPath());
            assertEquals(expectedStudentsMap.get(0), readenMap.get(0));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void dao_read_correct_properties_file() {
        try {
            Map<Integer, Theme> readenThemesMap = new HashMap<>();
            Map<Integer, Course> readenCoursesMap = new HashMap<>();
            yamlDao.readYamlConfig(TestPathEnum.CONFIG.getPath(), readenCoursesMap, readenThemesMap);
            assertEquals(expectedThemesMap.get(0), readenThemesMap.get(0));
            assertEquals(expectedCoursesMap.get(0), readenCoursesMap.get(0));
        } catch (IOException e) {
            fail();
        }
    }
}
