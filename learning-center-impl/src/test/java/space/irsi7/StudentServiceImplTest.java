package space.irsi7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import space.irsi7.enums.MenuEnum;
import space.irsi7.interfaces.Repositories.CoursesRepository;
import space.irsi7.interfaces.Repositories.ThemesRepository;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import space.irsi7.repository.CourseRepositoryImpl;
import space.irsi7.repository.StudentsRepositoryImpl;
import space.irsi7.repository.ThemesRepositoryImpl;
import space.irsi7.services.StudentServiceImpl;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    // TODO: Спросить про аннотацию @Mock и почему она не работает

    StudentsRepositoryImpl studentRepository = Mockito.mock(StudentsRepositoryImpl.class);
    CoursesRepository coursesRepository = Mockito.mock(CourseRepositoryImpl.class);
    ThemesRepository themesRepository = Mockito.mock(ThemesRepositoryImpl.class);

    StudentServiceImpl studentService = new StudentServiceImpl(studentRepository, coursesRepository, themesRepository);

//    @Test
//    public void constructor_throws_exception_without_source(){
//        assertThrows(RuntimeException.class, () -> {
//            var test = new StudentServiceImpl();
//        });
//    }

    @Test
    public void correct_time_left_with_some_mark() {
        Course testCourse = new Course(0, new ArrayList<>(List.of(0, 1)));
        Student testStudent = new Student(0, "Test Test", 0, new ArrayList<>(List.of(100)), 100);
        when(coursesRepository.getCourse(0)).thenReturn(testCourse);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);

        int answer = studentService.getEduTimeLeft(0);

        assertThat(answer, equalTo(1));
    }

    @Test
    public void correct_time_left_without_mark() {
        Course testCourse = new Course(0, new ArrayList<>(List.of(0, 1)));
        Student testStudent = new Student(0, "Test Test", 0, new ArrayList<>(), 100);
        when(coursesRepository.getCourse(0)).thenReturn(testCourse);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);

        int answer = studentService.getEduTimeLeft(0);

        assertThat(answer, equalTo(2));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Test Student",
            "",
            "Тест Студент",
            "11 22 33 44 55"
    })
    public void correct_stud_report_string(String name) {
        //init
        Student testStudent = new Student(0, name, 0, new ArrayList<>(List.of(100)), 100);
        Course testCourse = new Course(0, new ArrayList<>(List.of(0)));
        Theme testTheme = new Theme(0, "Test Theme 3", 3);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        when(coursesRepository.getCourse(0)).thenReturn(testCourse);
        when(themesRepository.getTheme(0)).thenReturn(testTheme);

        //when
        String answer = studentService.getReportStudent(0);

        //then
        assertThat(answer.contains(name), equalTo(true));
        assertThat(answer.contains("Test Theme 3"), equalTo(true));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            75, 80, 90, 100, 120, 99, 77
    })
    public void correct_stud_positive_drop_chance(int mark) {
        Student testStudent = new Student(0, "Test Test", 1, new ArrayList<>(List.of(mark)), mark);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        String answer = studentService.getDropChance(0);
        assertThat(answer, equalTo("Low probability to be expelled"));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            30, 65, 40, 74, 0, -5, -30
    })
    public void correct_stud_negative_drop_chance(int mark) {
        Student testStudent = new Student(0, "Test Test", 1, new ArrayList<>(List.of(mark)), mark);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        String answer = studentService.getDropChance(0);
        assertThat(answer, equalTo("High probability to be expelled"));
    }

    // TODO: Спросить про чёрный и белый ящики, считается ли мой тест за чёрный ящик
    @Test
    public void correct_validate_student() {
        when(studentRepository.containsStudent(0)).thenReturn(true);
        Boolean answer = studentService.validateId(0);
        assertThat(answer, equalTo(true));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            100, 95, 90, 80, 70, 60, 50, 150,
            30, 65, 40, 74, 0, -5, -30
    })
    public void correct_get_gpa_student(int gpa) {
        Student testStudent = new Student(0, "Test Test", 1, new ArrayList<>(List.of(gpa)), gpa);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        int answer = studentService.getGPA(0);
        assertThat(answer, equalTo(gpa));
    }

    @Test
    public void correct_direct_order_all_students() {
        ArrayList<Student> takenStudents = new ArrayList<>() {{
            add(new Student(1, "Test 1", 0));
            add(new Student(2, "Test 2", 0));
        }};
        int test_int = 0;
        when(studentRepository.getStudentSample(test_int, test_int)).thenReturn(takenStudents);
        var answer = studentService.getAllReport(test_int, MenuEnum.ORDER_DIRECT.ordinal(), test_int);
        assertThat(answer.get(0).contains("Test 1"), equalTo(true));
    }

    @Test
    public void correct_reversed_order_all_students() {
        ArrayList<Student> takenStudents = new ArrayList<>() {{
            add(new Student(1, "Test 1", 0));
            add(new Student(2, "Test 2", 0));
        }};
        int test_int = 0;
        when(studentRepository.getStudentSample(test_int, test_int)).thenReturn(takenStudents);
        var answer = studentService.getAllReport(test_int, MenuEnum.ORDER_REVERSED.ordinal(), test_int);
        assertThat(answer.get(0).contains("Test 2"), equalTo(true));
    }

}
