package space.irsi7.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import space.irsi7.LearningCenterApplication;
import space.irsi7.services.StudentServiceImpl;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(LearningCenterApplication.class);
        StudentServiceImpl studService =(StudentServiceImpl) ctx.getBean("studService");
        studService.getEduTimeLeft(2);
        System.out.println("cool day");
    }
}
