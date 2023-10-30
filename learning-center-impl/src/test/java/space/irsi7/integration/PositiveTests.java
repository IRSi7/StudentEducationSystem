package space.irsi7.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("API. Student. GET '/students'. Позитивные тесты получения информации о студентах")
public class PositiveTests extends TestBaseClass{

    @Test
    @DisplayName("API. Student. GET '/students/getStudent/0'. Получение информации о студенте 0 по ID")
    public void testSuccessGetBaseStudent(){
        String reportStudentString = studentService.getReportStudent(0);
        assertThat(reportStudentString.contains(" "), equalTo(true));
    }

    @Test
    @DisplayName("API. Student. GET '/students/getStudent/20'. Получение информации о студенте 20 по ID")
    public void testSuccessGetIncorrectStudent(){
        String reportStudentString = studentService.getReportStudent(20);
        assertThat(reportStudentString, equalTo("No such student"));
    }

    @Test
    @DisplayName("API. Student. GET '/students/getStudent/-1'. Получение информации о студенте -1 по ID")
    public void testSuccessGetNegativeStudent(){
        String reportStudentString = studentService.getReportStudent(-1);
        assertThat(reportStudentString, equalTo("No such student"));
    }

    @Test
    @DisplayName("API. Student. GET '/students/getEduTimeLeft/20'. Получение информации о оставшихся дней обучения для студента 20 по ID")
    public void testSuccessGetIncorrectStudentEduTimeLeft(){
        int testTime = studentService.getEduTimeLeft(20);
        assertThat(testTime, equalTo(-1));
    }

    @Test
    @DisplayName("API. Student. GET '/students/getEduTimeLeft/0'. Получение информации о оставшихся дней обучения для студента 0 по ID")
    public void testSuccessGetBaseStudentEduTimeLeft(){
        int testTime = studentService.getEduTimeLeft(0);
        assertThat(testTime, equalTo(0));
    }

    @Test
    @DisplayName("API. Student. GET '/students/getEduTimeLeft/-1'. Получение информации о оставшихся дней обучения для студента -1 по ID")
    public void testSuccessGetNegativeStudentEduTimeLeft(){
        int testTime = studentService.getEduTimeLeft(-1);
        assertThat(testTime, equalTo(-1));
    }
}
