package space.irsi7.interfaces;

import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface YamlDAO {

    void writeYAML(List<Object> arrayList, String path) throws IOException;

    Map<Integer, Student> readYamlStudents(String path) throws IOException;

    void readYamlConfig(String path,
                        Map<Integer, Course> courseMap,
                        Map<Integer, Theme> themeMap) throws IOException;
}
