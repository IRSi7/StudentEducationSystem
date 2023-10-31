package space.irsi7.models;

import java.util.Map;

public class Theme extends Readable {
    private int id;
    private String name;
    private int hours;

    public Theme(int id, String name, int hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }

    public Theme(Map<?, ?> theme){
        super(theme);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return id == theme.id && hours == theme.hours && name.equals(theme.name);
    }
}
