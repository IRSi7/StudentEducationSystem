package space.irsi7.aspects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import space.irsi7.StarterProperties;

@Component
public class LoggingPointcutStringBuilder {

    final
    StarterProperties properties;

    public LoggingPointcutStringBuilder(@Qualifier("starterProperties") StarterProperties properties) {
        this.properties = properties;
    }

    public String  getExpression(){
        var answer = new StringBuilder("execution(");
        var elements = properties.getStrings();
        answer.append(elements.get(0));
        for(int i = 1; i < elements.size(); i++){
            answer.append(") || execution(").append(elements.get(i));
        }
        answer.append(")");
        return answer.toString();
    }
}
