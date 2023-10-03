package space.irsi7.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.irsi7.dao.YamlDaoImpl;
import space.irsi7.enums.PathsEnum;
import space.irsi7.interfaces.StudentRepository;
import space.irsi7.models.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class StudentRepositoryImpl implements StudentRepository {

    private static int nextId = 0;

    final Map<Integer, Student> students;

    YamlDaoImpl yamlDaoImpl;

    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryImpl.class);

    public StudentRepositoryImpl() {
        this.yamlDaoImpl = new YamlDaoImpl();
        try {
            students = yamlDaoImpl.readYamlStudents(PathsEnum.STUDENTS.getPath());
            logger.info("Данные о студентах успешно считаны из students.yaml");
        } catch (IOException e) {
            logger.error("Ошибка при чтении данных из students.yaml");
            throw new RuntimeException(e);
        }
        nextId = students.keySet().stream().reduce(Integer::max).get() + 1;
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public Student getStudent(int id){
        return students.get(id);
    }

    public void saveNewStudent(String name, int course) {
        this.students.put(nextId, new Student(nextId, name, course));
        logger.info("Студент успешно добавлен к списку");
        nextId++;
        notifyChanges();
    }

    public void rateStudent(int studentId, int mark) {
        if (mark > 0 && mark < 101) {
            students.get(studentId).getMarks().add(mark);
            students.get(studentId).recountGPA();
            logger.info("Оценка за тест успешно добавлена");
            notifyChanges();
        } else {
            logger.error("Ошибка при добавлении оценки за тест");
        }
    }

    public void removeStudent(int id) {
        this.students.remove(id);
        logger.info("Студент успешно отчислен)");
        notifyChanges();
    }

    public boolean containsStudent(int id){
        return students.containsKey(id);
    }
    private void notifyChanges() {
        try {
            yamlDaoImpl.writeYAML(new ArrayList<>(students.values()), PathsEnum.STUDENTS.getPath());
            logger.info("Данные успешно записаны в students.yaml");
        } catch (IOException e) {
            logger.error("Ошибка записи данных в students.yaml");
        }
    }

}
