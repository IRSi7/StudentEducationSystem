package space.irsi7.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import space.irsi7.annotations.InjectRandomMarks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class InjectRandomMarksBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field: fields) {
            InjectRandomMarks annotation = field.getAnnotation(InjectRandomMarks.class);
            if(annotation != null){
                int min = annotation.min();
                int max = annotation.max();
                int amount = annotation.amount();
                List<Integer> injectedArray = new ArrayList<>();
                IntStream.range(1, amount).forEach(it -> {
                    Random random = new Random();
                    int i = min + random.nextInt(max - min);
                    injectedArray.add(i);
                });
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, injectedArray);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        return bean;
    }

}
