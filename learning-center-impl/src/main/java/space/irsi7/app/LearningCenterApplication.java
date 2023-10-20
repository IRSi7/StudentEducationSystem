package space.irsi7.app;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import space.irsi7.aop.MyAspect;
import space.irsi7.bpp.InjectRandomMarksBeanPostProcessor;
import space.irsi7.models.Student;
import space.irsi7.repository.StudentsRepositoryImpl;

import java.util.Arrays;
import java.util.stream.Collectors;

@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = "space.irsi7")
public class LearningCenterApplication {


//    public static void main(String[] args) {
//            SpringApplication.run(LearningCenterApplication.class, args);
//        }

    @Bean
    public Student studentBean(){
        return new Student();
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//            return args -> {
//                System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//                String[] beanNames = ctx.getBeanDefinitionNames();
//                Arrays.sort(beanNames);
//                for (String beanName : beanNames) {
//                    System.out.println(beanName);
//                }
//            };
//    }
}
