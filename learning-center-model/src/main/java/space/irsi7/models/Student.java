package space.irsi7.models;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.round;

public class Student extends Readable{

    //TODO: Спросить про int и Integer (Нужно ли стараться применять Integer)
    public int id;
    public String name;
    public int courseId;
    public ArrayList<Integer> marks;
    public int gpa;

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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Integer> marks) {
        this.marks = marks;
    }

    public int getGpa() {
        return gpa;
    }

    public void setGpa(int gpa) {
        this.gpa = gpa;
    }

    public Student(String name, int courseId, ArrayList<Integer> marks, int gpa) {

        this.name = name;
        this.courseId = courseId;
        this.marks = marks;
        this.gpa = gpa;
    }

    public Student(int id, String name, int courseId) {
        this.id = id;
        this.name = name;
        this.courseId = courseId;
        this.marks = new ArrayList<>();
        recountGPA();
    }

    public Student() {
        this.id = 0;
        this.name = null;
        this.courseId = 0;
        this.marks = new ArrayList<>();
        this.gpa = 0;
    }

    public Student(Map<?, ?> student){
        super(student);
    }

    @Override
    public String toString() {
        return " ID : " + id + " | Студент : " + name
                + " | Кол-во сданных тестов : " + marks.size()
                + " | Средний балл : " + gpa
                + " | Оценка успеваемости : "
                + ((gpa >= 75) ? "Низкая вероятность быть отчисленным" : "Высокая вероятность быть отчисленным");
    }

    public void recountGPA() {
        if (!this.marks.isEmpty()) {
            this.gpa = round((float) marks.stream().mapToInt(it -> it).sum() / this.marks.size());
        } else {
            this.gpa = 0;
        }
    }
}
