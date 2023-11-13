package space.irsi7.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import space.irsi7.enums.MenuEnum;
import space.irsi7.exceptions.StudentNotFoundException;
import space.irsi7.interfaces.repositories.*;
import space.irsi7.interfaces.StudentService;
import space.irsi7.models.*;

import java.util.*;

@Service("studService")
public class StudentServiceImpl implements StudentService {

    StudentsRepository studentRepository;
    ThemesRepository themesRepository;
    TestsRepository testsRepository;
    MarksRepository marksRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentsRepository studentRepository, ThemesRepository themesRepository, TestsRepository testsRepository, MarksRepository marksRepository) {
        this.studentRepository = studentRepository;
        this.themesRepository = themesRepository;
        this.testsRepository = testsRepository;
        this.marksRepository = marksRepository;
    }

    public boolean addStudent(String name, int course, int group) {
        return studentRepository.addStudent(name, course, group);
    }

    public boolean removeStudent(int id) {
        return studentRepository.removeStudent(id);
    }

    public boolean rateStudent(int studentId, int testId, int mark) {
        return marksRepository.addMark(studentId, testId, mark);
    }

    public Optional<Integer> getEduTimeLeft(int studentId) {
        Student student;
        try {
            student = studentRepository.getStudentById(studentId);
        } catch (StudentNotFoundException e) {
            return Optional.empty();
        }
        List<Theme> themes = themesRepository.getThemesByCourseId(student.course());
        List<Mark> marks = marksRepository.getMarksByStudentId(studentId);
        return Optional.of( themes.size() - marks.size());
    }

    @Override
    public Optional<StudentRest> getReport(int id) {
        Student student;
        try {
            student = studentRepository.getStudentById(id);
        } catch (StudentNotFoundException e) {
            return Optional.empty();
        }
        List<Mark> marks = marksRepository.getMarksByStudentId(id);
        List<Theme> themes = themesRepository.getThemesByCourseId(student.course());
        List<Test> tests = testsRepository.getTestsByStudentId(student.id());
        StudentRest answer = new StudentRest(student.id(), student.name(), student.course(), student.group(), themes, marks, tests);
        return Optional.of(answer);
    }

    public Optional<String> getDropChance(int studId) {
        return Optional.of((getGPA(studId) >= 75) ? "Low probability to be expelled" : "High probability to be expelled");
    }

    @Override
    public List<StudentRest> getAllReport(int sort, int order, int filter) {
        /*FIXME: Спросить про множественные запросы к БД.
        Лучше один раз считать всё, включая лишнее, и дальше ворочать этот массив данных или сделать много маленьких но точных запросов?
         */
        List<Student> students = studentRepository.getAllStudents();
        Map<Integer, Integer> gpa = new LinkedHashMap<>();
        students.forEach(it -> gpa.put(it.id(), getGPA(it.id())));

        List<StudentRest> answer = new ArrayList<>();


        students.stream().filter(s -> {
            if (filter == MenuEnum.FILTER_LOW.ordinal()) {
                return gpa.get(s.id()) < 75;
            }
            if (filter == MenuEnum.FILTER_HIGH.ordinal()) {
                return gpa.get(s.id()) >= 75;
            }
            return true;
        }).sorted((Student s, Student s1) -> {
            if (sort == MenuEnum.SORT_ID.ordinal()) {
                return Integer.compare(s.id(), s1.id());
            }
            if (sort == MenuEnum.SORT_NAME.ordinal()) {
                return s.name().compareTo(s1.name());
            }
            if (sort == MenuEnum.SORT_GPA.ordinal()) {
                return Integer.compare(gpa.get(s.id()), gpa.get(s1.id()));
            }
            return 0;
        }).forEach(it -> answer.add(getReport(it.id()).orElse(null)));

        if(order == MenuEnum.ORDER_REVERSED.ordinal()){
            Collections.reverse(answer);
        }

        return answer;
    }

    @Override
    public int getGPA(int studentId) {
        List<Mark> marks = marksRepository.getMarksByStudentId(studentId);
        int answer = 0;
        for (Mark mark : marks) {
            answer += mark.mark();
        }
        if (marks.isEmpty()) {
            return answer;
        }
        return answer / marks.size();
    }
}
