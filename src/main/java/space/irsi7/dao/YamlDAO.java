package space.irsi7.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.yaml.snakeyaml.Yaml;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class YamlDAO {

    ObjectMapper mapper = new YAMLMapper().enable(SerializationFeature.INDENT_OUTPUT);
    Yaml yaml = new Yaml();

    public void writeYAML(ArrayList arrayList, String path) throws IOException {
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

    public void readYamlConfig(String path ,
                               Map<Integer, Course> courseMap,
                               Map<Integer, Theme> themeMap) throws IOException {
        LinkedHashMap<String, Object> test = mapper.readValue(new File(path), new TypeReference<>() {} );
        System.out.println("Success");
        try {
            // Заполнение мапы courseMap
            //TODO: Спросить можно ли сделать так, чтобы IDEA не ругалась на unchecked cast
            ((ArrayList) test.get("courses")).forEach(c -> {
                Course course = new Course((LinkedHashMap) c);
                courseMap.put(course.getId(), course);
            });
            // Заполнение мапы themeMap
            ((ArrayList) test.get("themes")).forEach(c -> {
                Theme theme = new Theme((LinkedHashMap) c);
                themeMap.put(theme.getId(), theme);
            });
        } catch (ClassCastException e) {
            //TODO: Написать осмысленное сообщение
            throw new IllegalInitialDataException("Ошибка");
        }
    }

}
