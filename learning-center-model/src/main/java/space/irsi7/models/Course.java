package space.irsi7.models;

import java.util.*;

public class Course extends Readable {

    public int id;
    public ArrayList<Integer> themeIds;

    public Course(int id, ArrayList<Integer> themeIds){
        this.id = id;
        this.themeIds = themeIds;
    }

    public Course(Map<?, ?> course){
        super(course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && themeIds.equals(course.themeIds);
    }
}
