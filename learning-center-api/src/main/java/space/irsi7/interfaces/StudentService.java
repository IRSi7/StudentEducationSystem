package space.irsi7.interfaces;

import space.irsi7.exceptions.StudentNotFoundException;
import space.irsi7.models.Student;

import java.util.List;

public interface StudentService {

    void addStudent(String name, int course, int group);
    void removeStudent(int id);
    void rateStudent(int studentId, int testId, int mark);
    int getEduTimeLeft(int studentId);
    Student getStudent(int studId) throws StudentNotFoundException;
    String getDropChance(int studId);
    List<String> getAllReport(int sort, int order, int filter);
    int getGPA(int studId);

}
