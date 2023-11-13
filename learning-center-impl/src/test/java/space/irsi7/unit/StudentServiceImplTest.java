package space.irsi7.unit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import space.irsi7.enums.MenuEnum;
import space.irsi7.exceptions.StudentNotFoundException;
import space.irsi7.interfaces.repositories.*;
import space.irsi7.models.Course;
import space.irsi7.models.Mark;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import space.irsi7.repository.*;
import space.irsi7.services.StudentServiceImpl;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    // TODO: Спросить про аннотацию @Mock и почему она не работает

    StudentsRepository studentRepository = Mockito.mock(StudentsRepositoryImpl.class);
    MarksRepository marksRepository = Mockito.mock(MarksRepositoryImpl.class);
    ThemesRepository themesRepository = Mockito.mock(ThemesRepositoryImpl.class);
    TestsRepository testsRepository = Mockito.mock(TestsRepositoryImpl.class);
    StudentServiceImpl studentService = new StudentServiceImpl(studentRepository, themesRepository, testsRepository, marksRepository);


    @Test
    public void correct_time_left_with_some_mark() throws StudentNotFoundException {
        List<Theme> testThemes = List.of(
                new Theme(0, "Test Theme 1", 1),
                new Theme(1, "Test Theme 2", 2)
        );
        List<Mark> testMarks = List.of(
                new Mark(0, 0, 1)
        );
        Student testStudent = new Student(0, "Test Student", 0, 0);
        when(themesRepository.getThemesByCourseId(0)).thenReturn(testThemes);
        when(marksRepository.getMarksByStudentId(0)).thenReturn(testMarks);
        when(studentRepository.getStudentById(0)).thenReturn(testStudent);

        var answer = studentService.getEduTimeLeft(0);

        assertThat(answer.get(), equalTo(1));
    }

    @Test
    public void correct_time_left_without_mark() throws StudentNotFoundException {
        List<Theme> testThemes = List.of(
                new Theme(0, "Test Theme 1", 1),
                new Theme(1, "Test Theme 2", 2)
        );
        List<Mark> testMarks = List.of();
        Student testStudent = new Student(0, "Test Student", 0, 0);
        when(themesRepository.getThemesByCourseId(0)).thenReturn(testThemes);
        when(marksRepository.getMarksByStudentId(0)).thenReturn(testMarks);
        when(studentRepository.getStudentById(0)).thenReturn(testStudent);

        var answer = studentService.getEduTimeLeft(0);

        assertThat(answer.get(), equalTo(2));
    }


    @ParameterizedTest
    @ValueSource(ints = {
            75, 80, 90, 100, 120, 99, 77
    })
    public void correct_stud_positive_drop_chance(int mark) {
        when(marksRepository.getMarksByStudentId(0)).thenReturn(List.of(new Mark(0, 0, mark)));
        Optional<String> answer = studentService.getDropChance(0);
        assertThat(answer.get(), equalTo("Low probability to be expelled"));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            30, 65, 40, 74, 0, -5, -30
    })
    public void correct_stud_negative_drop_chance(int mark) {
        when(marksRepository.getMarksByStudentId(0)).thenReturn(List.of(new Mark(0, 0, mark)));
        Optional<String> answer = studentService.getDropChance(0);
        assertThat(answer.get(), equalTo("High probability to be expelled"));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            100, 95, 90, 80, 70, 60, 50, 150,
            30, 65, 40, 74, 0, -5, -30
    })
    public void correct_get_gpa_student(int gpa) {
        when(marksRepository.getMarksByStudentId(0)).thenReturn(List.of(new Mark(0, 0, gpa)));
        var answer = studentService.getGPA(0);
        assertThat(answer, equalTo(gpa));
    }

    @Test
    public void correct_direct_order_all_students() throws StudentNotFoundException {
        Student student1 = new Student(1, "Test Student 1", 0, 1);
        Student student2 = new Student(2, "Test Student 2", 0, 1);
        ArrayList<Student> takenStudents = new ArrayList<>() {{
            add(student1);
            add(student2);
        }};

        when(studentRepository.getAllStudents()).thenReturn(takenStudents);
        when(studentRepository.getStudentById(1)).thenReturn(student1);
        when(studentRepository.getStudentById(2)).thenReturn(student2);

        var answer = studentService.getAllReport(
                MenuEnum.SORT_ID.ordinal(),
                MenuEnum.ORDER_DIRECT.ordinal(),
                MenuEnum.FILTER_NO.ordinal());
        assertThat(answer.get(0).id(), equalTo(1));
    }
    @Test
    public void correct_reversed_order_all_students() throws StudentNotFoundException {
        Student student1 = new Student(1, "Test Student 1", 0, 1);
        Student student2 = new Student(2, "Test Student 2", 0, 1);
        ArrayList<Student> takenStudents = new ArrayList<>() {{
            add(student1);
            add(student2);
        }};

        when(studentRepository.getAllStudents()).thenReturn(takenStudents);
        when(studentRepository.getStudentById(1)).thenReturn(student1);
        when(studentRepository.getStudentById(2)).thenReturn(student2);

        var answer = studentService.getAllReport(
                MenuEnum.SORT_ID.ordinal(),
                MenuEnum.ORDER_REVERSED.ordinal(),
                MenuEnum.FILTER_NO.ordinal());
        assertThat(answer.get(0).id(), equalTo(2));
    }

}
