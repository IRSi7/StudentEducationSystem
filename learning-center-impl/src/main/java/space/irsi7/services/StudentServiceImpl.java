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
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class StudentServiceImpl implements StudentService {

    final StudentRepositoryImpl studentRepositoryImpl;
    final Map<Integer, Course> courses;
    final Map<Integer, Theme> themes;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl() throws IllegalInitialDataException {
        studentRepositoryImpl = new StudentRepositoryImpl();
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

    public void addStudent(String name, int course) {
        studentRepositoryImpl.saveNewStudent(name, course);
    }

    public void removeStudent(int id) {
        studentRepositoryImpl.removeStudent(id);
    }

    public void rateStudent(int studentId, int mark) {
        studentRepositoryImpl.rateStudent(studentId, mark);
    }

    public int getEduTimeLeft(int studentId) {
        Student curStudent = studentRepositoryImpl.getStudent(studentId);
        int passed = curStudent.getMarks().size();
        int all = courses.get(curStudent.getCourseId()).themeIds.size();
        return (all - passed);
    }

    public String getReportStudent(int studId) {

        Student curStudent = studentRepositoryImpl.getStudent(studId);
        StringBuilder answer = new StringBuilder("---------------------------------------------\n");
        answer.append("Студент: ")
                .append(curStudent.getName())
                .append("\n");
        answer.append("Тесты:\n");

        IntStream.range(0, curStudent.getMarks().size())
                .forEach(i -> answer.append("\t").append(i + 1)
                        .append(". | Тема: ")
                        .append(themes.get(courses.get(curStudent.getCourseId())
                                .themeIds.get(i + 1)).name)
                        .append(" | Оценка: ")
                        .append(studentRepositoryImpl.getStudent(studId).getMarks().get(i))
                        .append(" |\n"));
        answer.append("Средний балл: ").append(getGPA(studId)).append("\n");
        answer.append(("---------------------------------------------"));
        return answer.toString();
    }

    public Boolean getDropChance(int studId) {
        return studentRepositoryImpl.getStudent(studId).getGpa() >= 75;
    }

    public ArrayList<String> getAllReport(int sort, int order, int filter) {
        try {
            CopyOnWriteArrayList<Student> sortedStudents = new CopyOnWriteArrayList<>();
            ArrayList<String> answer = new ArrayList<>();

            ExecutorService service = Executors.newFixedThreadPool(studentRepositoryImpl.getStudents().size());

            studentRepositoryImpl.getStudents().values().stream()
                    .filter(s -> {
                        if (filter == MenuEnum.FILTER_LOW.ordinal()) {
                            return s.getGpa() < 75;
                        }
                        if (filter == MenuEnum.FILTER_HIGH.ordinal()) {
                            return s.getGpa() >= 75;
                        }
                        return true;
                    })
                    .forEach(s -> service.submit(() -> {
                        try {
                            Thread.sleep(3000);
                            sortedStudents.add(s);
                            logger.info("Поток сбора информации о студенте {} успешно завершён", s.getId());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }));

            service.shutdown();

            boolean done = service.awaitTermination(1, TimeUnit.MINUTES);
            logger.info("Формирование общего списка студентов успешно завершено");
            sortedStudents.stream()
                    .sorted((Student s, Student s1) -> {
                        if (sort == MenuEnum.SORT_ID.ordinal()) {
                            return Integer.compare(s.getId(), s1.getId());
                        }
                        if (sort == MenuEnum.SORT_NAME.ordinal()) {
                            return s.getName().compareTo(s1.getName());
                        }
                        if (sort == MenuEnum.SORT_TESTS_PASSED.ordinal()) {
                            return Integer.compare(s.getMarks().size(), s1.getMarks().size());
                        }
                        if (sort == MenuEnum.SORT_GPA.ordinal()) {
                            return Integer.compare(s.getGpa(), s1.getGpa());
                        }
                        return 0;
                    })
                    .forEach(s -> answer.add(s.toString()));

            if (order == MenuEnum.ORDER_REVERSED.ordinal()) {
                Collections.reverse(answer);
            }

            return answer;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateId(int id) {
        if (studentRepositoryImpl.containsStudent(id)) {
            return true;
        } else {
            System.out.println("Студента с таким ID не существует");
            return false;
        }
    }

    public int getGPA(int studId) {
        return studentRepositoryImpl.getStudent(studId).getGpa();
    }
}
