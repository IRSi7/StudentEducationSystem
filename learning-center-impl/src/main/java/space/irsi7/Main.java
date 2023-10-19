package space.irsi7;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import space.irsi7.app.MyAppContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.irsi7.models.Student;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(
                MyAppContextConfiguration.class);
        Student student = (Student) context.getBean("studentBean");
        System.out.println(student.marks.stream());
    }
}