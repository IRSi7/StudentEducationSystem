package space.irsi7.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.irsi7.interfaces.StudentService;

@SpringBootTest
public abstract class TestBaseClass {

    @Autowired
    StudentService studentService;

}
