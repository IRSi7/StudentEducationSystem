package space.irsi7.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import space.irsi7.app.LearningCenterApplication;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
public class MyAspect {

    private static final Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut("execution(public !void space.irsi7.*.*.*(*))")
    void loggableMethod(){ }

    @Around("execution(!void space.irsi7.*.*.*(*))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        logger.info("before " + joinPoint.toString() + ", args=[" + args + "]");
        System.out.println("Say something");
        var result = joinPoint.proceed();
        logger.info("return " + result.toString());
        return result;
    }

}
