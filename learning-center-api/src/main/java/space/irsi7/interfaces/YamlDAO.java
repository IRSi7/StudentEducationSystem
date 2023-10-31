package space.irsi7.interfaces;

import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public interface YamlDAO {

    void writeYAML(List<Object> arrayList, URL url) throws IOException;

    Map<Integer, Student> readYamlStudents(InputStream stream) throws IOException;

    void readYamlConfig(InputStream stream,
                        Map<Integer, Course> courseMap,
                        Map<Integer, Theme> themeMap) throws IOException;
}
