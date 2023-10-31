package space.irsi7.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomMarks {
    int min();
    int max();
    int amount();
}
