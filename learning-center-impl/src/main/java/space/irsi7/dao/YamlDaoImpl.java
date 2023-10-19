package space.irsi7.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import space.irsi7.interfaces.YamlDAO;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@Component
public class YamlDaoImpl implements YamlDAO {

    ObjectMapper mapper = new YAMLMapper().enable(SerializationFeature.INDENT_OUTPUT);
    Yaml yaml = new Yaml();

    public void writeYAML(List<Object> arrayList, URL url) throws IOException {
        try {
            File file = new File(url.toURI());
            FileWriter writer = new FileWriter(file);
            yaml.dump(arrayList, writer);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public Map<Integer, Student> readYamlStudents(InputStream stream) throws IOException {

        Map<Integer, Student> answer = new HashMap<>();
        //var stream = Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(path));
        ArrayList<?> studentsFile = mapper.readValue(stream, new TypeReference<>() {} );
        stream.close();
        studentsFile.forEach(sc -> {
            if (sc instanceof Map<?, ?> someClass) {
                Student student = new Student(someClass);
                answer.put(student.getId(), student);
            }
        });
        return answer;
    }


//    public Map<Integer, Student> getStudentsMap(String path) throws IOException {
//        return readYamlStudentsArray(path).stream().collect(Collectors.toMap(Student::getId, s -> s));
//    }

    public void readYamlConfig(InputStream stream,
                               Map<Integer, Course> courseMap,
                               Map<Integer, Theme> themeMap) throws IOException {

        //var stream = Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(path));
        Map<String, List<?>> configFile = mapper.readValue(stream, new TypeReference<>() {} );

        stream.close();
        // Заполнение мапы courseMap
        configFile.get("courses").forEach(sc -> {
            if (sc instanceof Map<?, ?> someClass) {
                Course course = new Course(someClass);
                courseMap.put(course.id, course);
            }
        });

        // Заполнение мапы themeMap
        configFile.get("themes").forEach(sc -> {
            if (sc instanceof Map<?, ?> someClass) {
                Theme theme = new Theme(someClass);
                themeMap.put(theme.id, theme);
            }
        });
    }

    public Map<Integer, Theme> readYamlThemes(InputStream stream) throws IOException {

        Map<String, List<?>> configFile = mapper.readValue(stream, new TypeReference<>() {} );
        Map<Integer, Theme> answer = new HashMap<>();

        stream.close();

        // Заполнение мапы themeMap
        configFile.get("themes").forEach(sc -> {
            if (sc instanceof Map<?, ?> someClass) {
                Theme theme = new Theme(someClass);
                answer.put(theme.id, theme);
            }
        });

        return answer;
    }

    public Map<Integer, Course> readYamlCourses(InputStream stream) throws IOException {

        Map<String, List<?>> configFile = mapper.readValue(stream, new TypeReference<>() {} );
        Map<Integer, Course> answer = new HashMap<>();

        stream.close();

        // Заполнение мапы themeMap
        configFile.get("courses").forEach(sc -> {
            if (sc instanceof Map<?, ?> someClass) {
                Course course = new Course(someClass);
                answer.put(course.id, course);
            }
        });

        return answer;
    }
}
