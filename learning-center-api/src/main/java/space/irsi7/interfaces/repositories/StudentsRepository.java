package space.irsi7.interfaces.repositories;

import space.irsi7.exceptions.StudentNotFoundException;
import space.irsi7.models.Student;
import java.util.List;

public interface StudentsRepository {

    Student getStudentById(int id) throws StudentNotFoundException;

    List<Student> getAllStudents();

    boolean addStudent(String name, int course, int group);

    boolean removeStudent(int id);
}
