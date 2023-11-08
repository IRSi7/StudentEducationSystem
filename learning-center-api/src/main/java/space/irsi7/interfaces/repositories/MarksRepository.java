package space.irsi7.interfaces.repositories;

import space.irsi7.models.Mark;

import java.util.List;

public interface MarksRepository {

    List<Mark> getMarksByStudentId(int studentId);

    Integer getMarksAmountByStudentId(int studentId);

    void addMark(int studentId, int testId, int mark);

    int getGpa(int studentId);

    List<Mark> getAllMarks();
}
