package space.irsi7.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.irsi7.dao.YamlDaoImpl;
import space.irsi7.enums.MenuEnum;
import space.irsi7.enums.PathsEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.interfaces.StudentService;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import space.irsi7.repository.StudentRepositoryImpl;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class StudentServiceImpl implements StudentService {

    final StudentRepositoryImpl studentRepository;
    final Map<Integer, Course> courses;
    final Map<Integer, Theme> themes;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl() throws IllegalInitialDataException {
        studentRepository = new StudentRepositoryImpl();
        try {
            var yamlDAO = new YamlDaoImpl();
            themes = new HashMap<>();
            courses = new HashMap<>();
            yamlDAO.readYamlConfig(PathsEnum.CONFIG.getPath(), courses, themes);
            logger.info("Данные о темах и курсах успешно считаны из config.yaml");
        } catch (ExceptionInInitializerError | IOException e) {
            logger.error("Ошибка при чтении файла config.yaml");
            throw new IllegalInitialDataException("Ошибка при чтении файла config.yaml", e);

        }
    }

    //TODO: Конструктор добавленный для тестирования... Нужно ли тестировать его ...
    public StudentServiceImpl(StudentRepositoryImpl studentRepository, Map<Integer, Course> courses, Map<Integer, Theme> themes) {
        this.studentRepository = studentRepository;
        this.courses = courses;
        this.themes = themes;
    }

    public void addStudent(String name, int course) {
        studentRepository.addStudent(name, course);
    }

    public void removeStudent(int id) {
        studentRepository.removeStudent(id);
    }

    public void rateStudent(int studentId, int mark) {
        studentRepository.rateStudent(studentId, mark);
    }

    public int getEduTimeLeft(int studentId) {
        Student curStudent = studentRepository.getStudent(studentId);
        int passed = curStudent.getMarks().size();
        int all = courses.get(curStudent.getCourseId()).themeIds.size();
        return (all - passed);
    }

    public String getReportStudent(int studId) {

        Student curStudent = studentRepository.getStudent(studId);
        StringBuilder answer = new StringBuilder("---------------------------------------------\n");
        answer.append("Студент: ")
                .append(curStudent.getName())
                .append("\n");
        answer.append("Тесты:\n");

        IntStream.range(0, curStudent.getMarks().size())
                .forEach(i -> answer.append("\t").append(i + 1)
                        .append(". | Тема: ")
                        .append(themes.get(courses.get(curStudent.getCourseId())
                                .themeIds.get(i)).name)
                        .append(" | Оценка: ")
                        .append(studentRepository.getStudent(studId).getMarks().get(i))
                        .append(" |\n"));
        answer.append("Средний балл: ").append(getGPA(studId)).append("\n");
        answer.append(("---------------------------------------------"));
        return answer.toString();
    }

    public Boolean getDropChance(int studId) {
        return studentRepository.getStudent(studId).getGpa() >= 75;
    }

    public List<String> getAllReport(int sort, int order, int filter) {
        List<String> answer = new ArrayList<>();
        studentRepository.getStudentSample(sort, filter) .forEach(s -> answer.add(s.toString()));
        if (order == MenuEnum.ORDER_REVERSED.ordinal()) {
            Collections.reverse(answer);
        }
        return answer;
    }

    public boolean validateId(int id) {
        return  studentRepository.containsStudent(id);
    }

    public int getGPA(int studId) {
        return studentRepository.getStudent(studId).getGpa();
    }
}
