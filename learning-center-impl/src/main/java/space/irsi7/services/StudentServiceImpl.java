package space.irsi7.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import space.irsi7.enums.MenuEnum;
import space.irsi7.interfaces.repositories.*;
import space.irsi7.interfaces.StudentService;
import space.irsi7.models.Mark;
import space.irsi7.models.Student;
import space.irsi7.models.Test;
import space.irsi7.models.Theme;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void addStudent(String name, int course, int group) {
        studentRepository.addStudent(name, course, group);
    }

    public void removeStudent(int id) {
        studentRepository.removeStudent(id);
    }

    public void rateStudent(int studentId, int testId, int mark) {
        marksRepository.addMark(studentId, testId, mark);
    }

    public int getEduTimeLeft(int studentId) {
        Student student = studentRepository.getStudentById(studentId);
        List<Theme> themes = themesRepository.getThemesByCourseId(student.course());
        List<Mark> marks = marksRepository.getMarksByStudentId(studentId);
        return  themes.size() - marks.size();
    }

    public HashMap<String, Object> getReport(int id) {
        Student student = studentRepository.getStudentById(id);
        List<Mark> marks = marksRepository.getMarksByStudentId(id);
        List<Theme> themes = themesRepository.getThemesByCourseId(student.course());
        List<Test> tests = testsRepository.getTestsByStudentId(student.id());

        HashMap<String, Object> answer = new HashMap<>();
        answer.put("student", student);
        answer.put("marks", marks);
        answer.put("themes", themes);
        answer.put("tests", tests);
        return answer;
    }

    public Student getStudent(int studId) {
        return studentRepository.getStudentById(studId);
    }

    public String getDropChance(int studId) {
        return (getGPA(studId) >= 75) ? "Low probability to be expelled" : "High probability to be expelled";
    }

    @Override
    public List<String> getAllReport(int sort, int order, int filter) {
        return null;
    }

//    public JSONArray getAllReport(int sort, int order, int filter) {
        /*FIXME: Спросить про множественные запросы к БД.
        Лучше один раз считать всё, включая лишнее, и дальше ворочать этот массив данных или сделать много маленьких но точных запросов?
         */
//        List<Student> students = studentRepository.getAllStudents();
//        Map<Integer, Integer> gpa = new HashMap<>();
//        students.forEach(it -> gpa.put(it.id(), getGPA(it.id())));
//        List<Test> tests = testsRepository.getAllTests();
//        List<Mark> marks = marksRepository.getAllMarks();
//
//        JSONArray answer = new JSONArray();
////        students = students.stream()
////                .filter( s -> {
////                    if(filter == MenuEnum.FILTER_LOW.ordinal()){
////                        return gpa.get(s.id()) < 75;
////                    }
////                    if(filter == MenuEnum.FILTER_HIGH.ordinal()){
////                        return gpa.get(s.id()) >= 75;
////                    }
////                    return true;
////                })
////                .sorted((Student s, Student s1) -> {
////                    if(sort == MenuEnum.SORT_ID.ordinal()){
////                        return Integer.compare(s.id(), s1.id());
////                    }
////                    if(sort == MenuEnum.SORT_NAME.ordinal()){
////                        return s.name().compareTo(s1.name());
////                    }
//////                    if(sort == MenuEnum.SORT_TESTS_PASSED.ordinal()){
//////                        return Integer.compare(s.().size(), s1.getMarks().size());
//////                    }
////                    if(sort == MenuEnum.SORT_GPA.ordinal()){
////                        return Integer.compare(gpa.get(s.id()), gpa.get(s1.id()));
////                    }
////                    return 0;
////                }).toList();
//        students.forEach(it -> {
//
//            answer.add();
//        });
//        return answer;
//    }
//    public boolean validateId(int id) {
//        return studentRepository.containsStudent(id);
//    }

//    JSONObject createReportStudentJson(
//            Student student,
//            int gpa,
//            List<Mark> marks,
//            List<Test> tests,
//            List<Theme> themes) {
//        JSONObject answer = new JSONObject();
//        answer.put("id", student.id());
//        answer.put("name", student.name());
//        answer.put("course", student.course());
//        answer.put("group", student.group());
//        answer.put("gpa", gpa);
//        answer.put("marks", marks);
//        answer.put("tests", tests);
//        answer.put("themes", themes);
//    }

    @Override
    public int getGPA(int studentId) {
        List<Mark> marks = marksRepository.getMarksByStudentId(studentId);
        AtomicInteger answer = new AtomicInteger();
        marks.forEach(it -> answer.addAndGet(it.mark()));
        return answer.get();
    }
}
