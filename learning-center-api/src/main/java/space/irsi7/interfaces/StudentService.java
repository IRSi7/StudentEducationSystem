package space.irsi7.interfaces;

import java.util.List;

public interface StudentService {

    void addStudent(String name, int course);
    void removeStudent(int id);
    void rateStudent(int studentId, int mark);
    int getEduTimeLeft(int studentId);
    String getReportStudent(int studId);
    Boolean getDropChance(int studId);
    List<String> getAllReport(int sort, int order, int filter);
    int getGPA(int studId);

}
