package space.irsi7.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SpringBootTest
@DisplayName("API. Student. GET '/students'")
@AutoConfigureMockMvc
public class GetTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/create_all_tables.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/init.sql"));
        }
    }

    @AfterAll
    public static void erase(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/delete_all_tables.sql"));
        }
    }

//    @Sql("/sql/reset.sql")
//    @AfterEach
//    public void reset(){
//    }

    @Test
    @DisplayName("API. Student. GET '/students/0'. Получение информации о студенте 0 по ID")
    public void testSuccessGetBaseStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Student 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.course").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group").value(1));
    }

    @Test
    @DisplayName("API. Student. GET '/students/20'. Получение информации о студенте 20 по ID")
    public void testSuccessGetIncorrectStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/20"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("API. Student. GET '/students/-1'. Получение информации о студенте -1 по ID")
    public void testSuccessGetNegativeStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("API. Student. GET '/students/EduTimeLeft/20'. Получение информации о оставшихся дней обучения для студента 20 по ID")
    public void testSuccessGetIncorrectStudentEduTimeLeft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/20"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("API. Student. GET '/students/EduTimeLeft/0'. Получение информации о оставшихся дней обучения для студента 0 по ID")
    public void testSuccessGetBaseStudentEduTimeLeft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/EduTimeLeft/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }
//
//    @Test
//    @DisplayName("API. Student. GET '/students/getEduTimeLeft/-1'. Получение информации о оставшихся дней обучения для студента -1 по ID")
//    public void testSuccessGetNegativeStudentEduTimeLeft(){
//        int testTime = studentService.getEduTimeLeft(-1);
//        assertThat(testTime, equalTo(-1));
//    }
}
