package space.irsi7;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import space.irsi7.aspects.LoggingInterceptor;
import space.irsi7.aspects.LoggingPointcutStringBuilder;

@EnableConfigurationProperties(StarterProperties.class)
//@ConditionalOnProperty(value = "logger.strings")
@ComponentScan
@Configuration
public class StarterConfiguration {

    @Bean("advisorBean")
    public Advisor advisorBean(LoggingPointcutStringBuilder builder, LoggingInterceptor interceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(builder.getExpression());
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
