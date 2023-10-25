package space.irsi7.controllers;

import org.springframework.web.bind.annotation.*;
import space.irsi7.interfaces.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public void addStudent(@RequestParam String name, int courseId) {
        studentService.addStudent(name, courseId);
    }

    @PostMapping("/dropStudent/{id}")
    public void dropStudent(@PathVariable int id) {
        studentService.removeStudent(id);
    }

    @PostMapping("/rateStudent/{id}")
    public void rateStudent(@PathVariable int id, int mark) {
        studentService.rateStudent(id, mark);
    }

    @GetMapping("/getEduTimeLeft/{id}")
    public int getEduTimeLeft(@PathVariable int id) {
        return studentService.getEduTimeLeft(id);
    }

    @GetMapping("/getDropChance/{id}")
    public String getDropChance(@PathVariable int id) {
        return studentService.getDropChance(id);
    }

    @GetMapping("/getReport/{id}")
    public String getReport(@PathVariable int id) {
        return studentService.getReportStudent(id);
    }

    @GetMapping("/getAllReport")
    public String getAllReport(@RequestParam int sort, int order, int filter) {
        return studentService.getAllReport(sort, order, filter).toString();
    }

    @GetMapping("/AmIRunning")
    public String test() {
        return "I am running";
    }
}