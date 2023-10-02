package space.irsi7.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.yaml.snakeyaml.Yaml;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class YamlDAO {

    ObjectMapper mapper = new YAMLMapper().enable(SerializationFeature.INDENT_OUTPUT);
    Yaml yaml = new Yaml();

    public void writeYAML(List<Object> arrayList, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        yaml.dump(arrayList, writer);
    }

    public ArrayList<Student> readYamlStudentsArray(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<>() {
        });
    }


    public Map<Integer, Student> getStudentsMap(String path) throws IOException {
        return readYamlStudentsArray(path).stream().collect(Collectors.toMap(Student::getId, s -> s));
    }

    public void readYamlConfig(String path,
                               Map<Integer, Course> courseMap,
                               Map<Integer, Theme> themeMap) throws IOException {
        Map<String, List<?>> configFile = mapper.readValue(new File(path), new TypeReference<>() {
        });
        System.out.println("Success");
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
}
