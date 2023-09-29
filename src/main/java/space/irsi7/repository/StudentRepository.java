package space.irsi7.repository;

import space.irsi7.dao.YamlDAO;
import space.irsi7.enums.LogsEnum;
import space.irsi7.enums.PathsEnum;
import space.irsi7.models.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class StudentRepository {

    private static int nextId = 0;

    final Map<Integer, Student> students;

    YamlDAO yamlDAO;

    public StudentRepository() throws IOException {
        this.yamlDAO = new YamlDAO();
        students = yamlDAO.getStudentsMap(PathsEnum.STUDENTS.getPath());
        nextId = students.keySet().stream().reduce(Integer::max).get() + 1;
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public Student getStudent(int id){
        return students.get(id);
    }

    public void saveNewStudent(String name, int course) {
        this.students.put(nextId, new Student(nextId, name, course));
        nextId++;
        notifyChanges();
    }

    public void rateStudent(int studentId, int mark) {
        if (mark > 0 && mark < 101) {
            students.get(studentId).getMarks().add(mark);
            students.get(studentId).recountGPA();
            notifyChanges();
        } else {
            System.out.println(LogsEnum.INSERT_FAIL.getMessage());
        }
    }

    public void removeStudent(int id) {
        this.students.remove(id);
        notifyChanges();
    }

    public boolean containsStudent(int id){
        return students.containsKey(id);
    }
    private void notifyChanges() {
        try {
            yamlDAO.writeYAML(new ArrayList<>(students.values()), PathsEnum.STUDENTS.getPath());
            System.out.println(LogsEnum.INSERT_SUCCESS.getMessage());
        } catch (IOException e) {
            System.out.println(LogsEnum.INSERT_FAIL.getMessage());
        }
    }

}
