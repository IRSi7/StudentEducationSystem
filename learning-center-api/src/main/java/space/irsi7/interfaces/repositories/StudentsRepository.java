package space.irsi7.interfaces.repositories;

import space.irsi7.models.Student;
import java.util.List;

public interface StudentsRepository {

    Student getStudentById(int id);

    List<Student> getAllStudents();

    void addStudent(String name, int course, int group);

    void removeStudent(int id);
}
