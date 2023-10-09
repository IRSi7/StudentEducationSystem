package space.irsi7;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import space.irsi7.dao.YamlDaoImpl;
import space.irsi7.enums.MenuEnum;
import space.irsi7.models.Student;
import space.irsi7.repository.StudentRepositoryImpl;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class StudentRepositoryImplTest {

    static StudentRepositoryImpl studentRepository;

    @BeforeAll
    static void init() {
        studentRepository = new StudentRepositoryImpl(new HashMap<>(), Mockito.mock(YamlDaoImpl.class));
    }

    @Test
    public void successful_add_new_student() {
        int index = studentRepository.getNextId();
        studentRepository.addStudent("Test", 0);
        var testStudent = studentRepository.getStudent(index);
        assertEquals("Test", testStudent.name);
        assertEquals(0, testStudent.courseId);
        assertEquals(index, testStudent.id);
    }

    @Test
    public void successful_remove_student() {
        int index = studentRepository.getNextId();
        studentRepository.addStudent("Test", 0);
        studentRepository.removeStudent(index);
        assertFalse(studentRepository.containsStudent(index));
    }

    @Test
    public void correct_rate_student() {
        int index = studentRepository.getNextId();
        studentRepository.addStudent("Test", 0);
        studentRepository.rateStudent(index, 100);
        Student student = studentRepository.getStudent(index);
        // TODO: Есть ли более изящный способ получить последний элемент коллекции, или ждать Java 21?)
        assertEquals(100, student.marks.get(student.marks.size() - 1));
    }

    @Test
    public void correct_sort_by_id() {
        init_test_students_with_marks();

        ArrayList<Student> sample = studentRepository.getStudentSample(MenuEnum.SORT_ID.ordinal(), MenuEnum.FILTER_NO.ordinal());
        assertThat(sample.get(0).name, equalTo("Test student 1"));
        assertThat(sample.get(1).name, equalTo("Test student 2"));
        assertThat(sample.get(2).name, equalTo("Test student 3"));
    }

    @Test
    public void correct_sort_by_passed() {
        init_test_students_with_marks();

        ArrayList<Student> sample = studentRepository.getStudentSample(MenuEnum.SORT_TESTS_PASSED.ordinal(), MenuEnum.FILTER_NO.ordinal());
        assertThat(sample.get(0).name, equalTo("Test student 4"));
        assertThat(sample.get(1).name, equalTo("Test student 2"));
        assertThat(sample.get(2).name, equalTo("Test student 1"));
    }

    @Test
    public void correct_sort_by_gpa() {
        init_test_students_with_marks();

        ArrayList<Student> sample = studentRepository.getStudentSample(MenuEnum.SORT_GPA.ordinal(), MenuEnum.FILTER_NO.ordinal());
        assertThat(sample.get(0).name, equalTo("Test student 4"));
        assertThat(sample.get(1).name, equalTo("Test student 3"));
        assertThat(sample.get(2).name, equalTo("Test student 1"));
    }

    @Test
    public void correct_sort_by_name() {
        init_test_students_with_marks();

        ArrayList<Student> sample = studentRepository.getStudentSample(MenuEnum.SORT_NAME.ordinal(), MenuEnum.FILTER_NO.ordinal());
        assertThat(sample.get(0).name, equalTo("Test student 1"));
        assertThat(sample.get(1).name, equalTo("Test student 2"));
        assertThat(sample.get(2).name, equalTo("Test student 3"));
    }

    @Test
    public void correct_low_filter() {
        init_test_students_with_marks();

        ArrayList<Student> sample = studentRepository.getStudentSample(MenuEnum.SORT_ID.ordinal(), MenuEnum.FILTER_LOW.ordinal());
        assertThat(sample.size(), equalTo(1));
        assertThat(sample.get(0).name, equalTo("Test student 4"));
    }

    @Test
    public void correct_high_filter() {
        init_test_students_with_marks();

        ArrayList<Student> sample = studentRepository.getStudentSample(MenuEnum.SORT_ID.ordinal(), MenuEnum.FILTER_HIGH.ordinal());
        assertThat(sample.size(), equalTo(3));
    }

    //TODO: Спросить почему этот тест выполняется дольше всех (280мс)
    @Test
    public void empty_constructor_throws_exception() {
        assertThrows(RuntimeException.class, () -> studentRepository = new StudentRepositoryImpl());
    }

    private void init_test_students_with_marks() {
        init();
        int index = studentRepository.getNextId();
        studentRepository.addStudent("Test student 1", 0);
        studentRepository.rateStudent(index, 90);
        studentRepository.rateStudent(index, 90);

        index = studentRepository.getNextId();
        studentRepository.addStudent("Test student 2", 0);
        studentRepository.rateStudent(index, 100);

        index = studentRepository.getNextId();
        studentRepository.addStudent("Test student 3", 0);
        studentRepository.rateStudent(index, 75);
        studentRepository.rateStudent(index, 80);
        studentRepository.rateStudent(index, 85);

        index = studentRepository.getNextId();
        studentRepository.addStudent("Test student 4", 0);
        studentRepository.rateStudent(index, 60);
    }
}
