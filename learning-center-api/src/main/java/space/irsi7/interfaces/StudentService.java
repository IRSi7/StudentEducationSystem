package space.irsi7.interfaces;

import space.irsi7.exceptions.StudentNotFoundException;
import space.irsi7.models.Student;
import space.irsi7.models.StudentRest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    boolean addStudent(String name, int course, int group);
    boolean removeStudent(int id);
    boolean rateStudent(int studentId, int testId, int mark);
    Optional<Integer> getEduTimeLeft(int studentId);

    Optional<StudentRest> getReport(int id);
    Optional<String> getDropChance(int studId);
    List<StudentRest> getAllReport(int sort, int order, int filter);
    int getGPA(int studId);

}
