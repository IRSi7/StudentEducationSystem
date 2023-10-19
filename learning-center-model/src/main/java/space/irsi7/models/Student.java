package space.irsi7.models;

import space.irsi7.annotations.InjectRandomMarks;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.round;

public class Student extends Readable{

    public int id;

    public String name;
    public int courseId;
    @InjectRandomMarks(min = 20, max = 80, amount = 6)
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

    public Student(int id, String name, int courseId, ArrayList<Integer> marks, int gpa) {

        this.id = id;
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
        return " ID : " + id + " | Student : " + name
                + " | Test passed : " + marks.size()
                + " | GPA : " + gpa
                + " | Acceptable : "
                + ((gpa >= 75) ? "Low probability to be expelled" : "High probability to be expelled")
                + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && courseId == student.courseId && gpa == student.gpa && name.equals(student.name) && marks.equals(student.marks);
    }

    public void recountGPA() {
        if (!this.marks.isEmpty()) {
            this.gpa = round((float) marks.stream().mapToInt(it -> it).sum() / this.marks.size());
        } else {
            this.gpa = 0;
        }
    }
}