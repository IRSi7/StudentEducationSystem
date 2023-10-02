package space.irsi7.interfaces;

import space.irsi7.exceptions.IllegalInitialDataException;

import java.util.Arrays;
import java.util.Map;

public abstract class Readable {

    protected Readable() {}

    protected Readable(Map<?, ?> theme) {
        Arrays.stream(this.getClass().getFields()).forEach(field -> {
            if (theme.containsKey(field.getName())) {
                try {
                    field.set(this, theme.get(field.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalInitialDataException("Ошибка при чтении переменной " + field.getName() + " из класса " + this.getClass().getName());
            }
        });
    }

}
