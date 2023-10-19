package space.irsi7;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import space.irsi7.services.StudentServiceImpl;

public class MyWebAppInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("space.irsi7.app.MyAppContextConfiguration");

        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(context));

//        Student student = (Student) context.getBean("studentBean");
        dispatcher.addMapping("/");
    }
}
