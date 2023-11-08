package space.irsi7.interfaces.repositories;

import space.irsi7.models.Test;

import java.util.List;

public interface TestsRepository {
    Test getTestById(int id);

    List<Test> getTestsByStudentId(int id);

    List<Test> getAllTests();
}
