package space.irsi7.interfaces;

import space.irsi7.models.Student;

import java.util.Map;

public interface StudentRepository {

    Map<Integer, Student> getStudents();

    Student getStudent(int id);

    void saveNewStudent(String name, int course);

    void rateStudent(int studentId, int mark);

    void removeStudent(int id);

    boolean containsStudent(int id);

}
