package space.irsi7.aspects;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class LoggingInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String args = Arrays.stream(invocation.getArguments())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        logger.info("before " + invocation + ", args = [" + args + "]");
        var result = invocation.proceed();
        assert result != null;
        logger.info("result = [" + result + "]");
        return result;
    }
}
