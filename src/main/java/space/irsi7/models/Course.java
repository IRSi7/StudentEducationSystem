package space.irsi7.models;

import space.irsi7.exceptions.IllegalInitialDataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Course {

    public int id;
    public ArrayList<Integer> themeIds;

    public Course(int id, ArrayList<Integer> themeIds){
        this.id = id;
        this.themeIds = themeIds;
    }

    public Course(){
        this.id = 0;
        this.themeIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(ArrayList<Integer> themeIds) {
        this.themeIds = themeIds;
    }

    //TODO:Спросить про возможность выноса этого метода в абстрактный класс
    public Course(LinkedHashMap theme){
        Arrays.stream(this.getClass().getFields()).forEach( field -> {
            System.out.println(field.getName());
            if(theme.containsKey(field.getName())){
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
