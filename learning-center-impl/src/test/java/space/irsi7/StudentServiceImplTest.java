package space.irsi7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import space.irsi7.enums.MenuEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import space.irsi7.repository.StudentRepositoryImpl;
import space.irsi7.services.StudentServiceImpl;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    // TODO: Спросить про аннотацию @Mock и почему она не работает

    StudentRepositoryImpl studentRepository = Mockito.mock(StudentRepositoryImpl.class);

    StudentServiceImpl studentService = new StudentServiceImpl(
            studentRepository,
            new HashMap<>() {{
                put(0, new Course(0, new ArrayList<>(List.of(0, 1))));
                put(1, new Course(0, new ArrayList<>(List.of(2))));
            }},
            new HashMap<>() {{
                put(0, new Theme(0, "Test Theme 1", 0));
                put(1, new Theme(1, "Test Theme 2", 0));
                put(2, new Theme(1, "Test Theme 3", 0));
            }}
    );

    @Test
    public void constructor_throws_exception_without_source(){
        assertThrows(RuntimeException.class, () -> {
            var test = new StudentServiceImpl();
        });
    }

    @Test
    public void correct_time_left_with_some_mark() {
        Student testStudent = new Student(0, "Test Test", 0, new ArrayList<>(List.of(100)), 100);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        int answer = studentService.getEduTimeLeft(0);
        assertThat(answer, equalTo(1));
    }

    @Test
    public void correct_time_left_without_mark() {
        Student testStudent = new Student(0, "Test Test", 0, new ArrayList<>(), 100);
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
        Student testStudent = new Student(0, name, 1, new ArrayList<>(List.of(100)), 100);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        String answer = studentService.getReportStudent(0);
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
        Boolean answer = studentService.getDropChance(0);
        assertThat(answer, equalTo(true));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            30, 65, 40, 74, 0, -5, -30
    })
    public void correct_stud_negative_drop_chance(int mark) {
        Student testStudent = new Student(0, "Test Test", 1, new ArrayList<>(List.of(mark)), mark);
        when(studentRepository.getStudent(0)).thenReturn(testStudent);
        Boolean answer = studentService.getDropChance(0);
        assertThat(answer, equalTo(false));
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
