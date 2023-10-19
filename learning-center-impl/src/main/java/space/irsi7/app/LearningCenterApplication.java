package space.irsi7.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import space.irsi7.bpp.InjectRandomMarksBeanPostProcessor;
import space.irsi7.models.Student;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "space.irsi7")
public class LearningCenterApplication {


        public static void main(String[] args) {
            SpringApplication.run(LearningCenterApplication.class, args);
        }

    @Bean
    public InjectRandomMarksBeanPostProcessor eventBusBeanPostProcessor() {
        return new InjectRandomMarksBeanPostProcessor();
    }

    @Bean
    public Student studentBean(){
        return new Student();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
            return args -> {
                System.out.println("Let's inspect the beans provided by Spring Boot:");

                String[] beanNames = ctx.getBeanDefinitionNames();
                Arrays.sort(beanNames);
                for (String beanName : beanNames) {
                    System.out.println(beanName);
                }

            };
        }
}
