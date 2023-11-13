//package space.irsi7.integration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.SqlGroup;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.test.web.servlet.MockMvc;
//import space.irsi7.interfaces.StudentService;
//
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//
//@SpringBootTest
//@SqlGroup({
//        @Sql(value = "classpath:sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
//        @Sql(value = "classpath:sql/init.sql", executionPhase = BEFORE_TEST_METHOD)
//})
//@AutoConfigureWebTestClient
//public abstract class TestBaseClass {
//
//    @Autowired
//    StudentService studentService;
//
//}
