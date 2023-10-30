package space.irsi7.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import space.irsi7.enums.MenuEnum;
import space.irsi7.interfaces.Repositories.CoursesRepository;
import space.irsi7.interfaces.Repositories.StudentsRepository;
import space.irsi7.interfaces.Repositories.ThemesRepository;
import space.irsi7.interfaces.StudentService;
import space.irsi7.models.Student;

import java.util.*;
import java.util.stream.IntStream;

@Service("studService")
public class StudentServiceImpl implements StudentService {

    StudentsRepository studentRepository;

    CoursesRepository coursesRepository;
    ThemesRepository themesRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    //TODO: Конструктор добавленный для тестирования... Нужно ли тестировать его ...
    @Autowired
    public StudentServiceImpl(StudentsRepository studentRepository,
                              CoursesRepository coursesRepository,
                              ThemesRepository themesRepository) {
        this.studentRepository = studentRepository;
        this.themesRepository = themesRepository;
        this.coursesRepository = coursesRepository;
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
        if (curStudent == null){
            return -1;
        }
        int passed = curStudent.getMarks().size();
        int all = coursesRepository.getCourse(curStudent.getCourseId()).themeIds.size();
        return (all - passed);
    }

    public String getReportStudent(int studId) {

        Student curStudent = studentRepository.getStudent(studId);
        if(curStudent == null){
            return "No such student";
        }
        StringBuilder answer = new StringBuilder();
        answer.append("Student: ")
                .append(curStudent.getName())
                .append("\n");
        answer.append("| Tests: \n");

        IntStream.range(0, curStudent.getMarks().size())
                .forEach(i -> answer.append("\t").append(i + 1)
                        .append(". | Theme: ")
                        .append(themesRepository.getTheme(coursesRepository.getCourse(curStudent.getCourseId())
                                .themeIds.get(i)).name)
                        .append(" | Mark: ")
                        .append(studentRepository.getStudent(studId).getMarks().get(i))
                        .append(" |\n"));
        answer.append("GPA: ").append(getGPA(studId)).append("\n");
        return answer.toString();
    }

    public String getDropChance(int studId) {
        return (studentRepository.getStudent(studId).getGpa() >= 75) ? "Low probability to be expelled" : "High probability to be expelled";
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
