package space.irsi7.models;

import space.irsi7.exceptions.IllegalInitialDataException;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Theme {
    public int id;
    public String name;
    public int hours;

    public Theme() {
        this.id = 0;
        this.name = null;
        this.hours = 0;
    }

    public Theme(int id, String name, int hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    //TODO:Спросить про возможность выноса этого метода в абстрактный класс
    public Theme(LinkedHashMap theme){
        Arrays.stream(this.getClass().getFields()).forEach( field -> {
            if(theme.containsKey(field.getName())){
                try {
                    field.set(this, theme.get(field.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalInitialDataException("Ошибка при чтении переменной " + field.getName() + " из класса " + Theme.class.getName());
            }
        });
    }
}
