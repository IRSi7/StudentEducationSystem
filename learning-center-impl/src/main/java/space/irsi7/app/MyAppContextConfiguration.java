package space.irsi7.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import space.irsi7.bpp.InjectRandomMarksBeanPostProcessor;
import space.irsi7.models.Student;

@Configuration
@ComponentScan(basePackages = "space.irsi7")
public class MyAppContextConfiguration {  // (1)

    @Bean
    public InjectRandomMarksBeanPostProcessor eventBusBeanPostProcessor() {
        return new InjectRandomMarksBeanPostProcessor();
    }

    @Bean
    public Student studentBean(){
        return new Student();
    }

}
