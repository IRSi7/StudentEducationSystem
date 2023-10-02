package space.irsi7.models;

import space.irsi7.interfaces.Readable;

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
}
