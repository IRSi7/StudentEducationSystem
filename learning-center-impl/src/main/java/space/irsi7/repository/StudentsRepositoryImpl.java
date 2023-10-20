package space.irsi7.repository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;
import space.irsi7.dao.YamlDaoImpl;
import space.irsi7.enums.MenuEnum;
import space.irsi7.enums.PathsEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.interfaces.Repositories.StudentsRepository;
import space.irsi7.models.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentsRepositoryImpl implements StudentsRepository {

    private static int nextId = 0;

    Map<Integer, Student> students;

    final
    YamlDaoImpl yamlDaoImpl;

    private static final Logger logger = LoggerFactory.getLogger(StudentsRepositoryImpl.class);

    public StudentsRepositoryImpl(YamlDaoImpl yamlDaoImpl) {
        this.yamlDaoImpl = yamlDaoImpl;
        this.students = new HashMap<>();
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public Student getStudent(int id){
        return students.get(id);
    }

    public int getNextId() {
        return nextId;
    }

    public void addStudent(String name, int course) {
        this.students.put(nextId, new Student(nextId, name, course));
//        logger.info("Студент успешно добавлен к списку");
        nextId++;
        notifyChanges();
    }

    public void rateStudent(int studentId, int mark) {
        if (mark > 0 && mark < 101) {
            students.get(studentId).getMarks().add(mark);
            students.get(studentId).recountGPA();
//            logger.info("Оценка за тест успешно добавлена");
            notifyChanges();
        } else {
//            logger.error("Ошибка при добавлении оценки за тест");
        }
    }

    public void removeStudent(int id) {
        this.students.remove(id);
//        logger.info("Студент успешно отчислен)");
        notifyChanges();
    }

    public boolean containsStudent(int id){
        return students.containsKey(id);
    }
    public ArrayList<Student> getStudentSample(int sort, int filter){
        return new ArrayList<>(students.values().stream()
                .filter( s -> {
                    if(filter == MenuEnum.FILTER_LOW.ordinal()){
                        return s.getGpa() < 75;
                    }
                    if(filter == MenuEnum.FILTER_HIGH.ordinal()){
                        return s.getGpa() >= 75;
                    }
                    return true;
                })
                .sorted((Student s, Student s1) -> {
                    if(sort == MenuEnum.SORT_ID.ordinal()){
                        return Integer.compare(s.getId(), s1.getId());
                    }
                    if(sort == MenuEnum.SORT_NAME.ordinal()){
                        return s.getName().compareTo(s1.getName());
                    }
                    if(sort == MenuEnum.SORT_TESTS_PASSED.ordinal()){
                        return Integer.compare(s.getMarks().size(), s1.getMarks().size());
                    }
                    if(sort == MenuEnum.SORT_GPA.ordinal()){
                        return Integer.compare(s.getGpa(), s1.getGpa());
                    }
                    return 0;
                })
                .toList());
    }
    private void notifyChanges() {
        try {

            yamlDaoImpl.writeYAML(new ArrayList<>(students.values()),
                    this.getClass().getClassLoader().getResource(PathsEnum.STUDENTS.getPath()));
//            logger.info("Данные успешно записаны в students.yaml");
        } catch (IOException e) {
            logger.error("Ошибка записи данных в students.yaml");
        }
    }

    @PostConstruct
    public void init() {
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(PathsEnum.STUDENTS.getPath());

            students = yamlDaoImpl.readYamlStudents(stream);
            nextId = students.keySet().stream().reduce(Integer::max).get() + 1;
//            logger.info("Данные о студентах успешно считаны из students.yaml");
        } catch (Exception e) {
            logger.error("Ошибка при чтении данных из students.yaml");
            throw new IllegalInitialDataException(e);
        }
    }

}
