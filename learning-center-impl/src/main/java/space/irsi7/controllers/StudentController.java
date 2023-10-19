package space.irsi7.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import space.irsi7.interfaces.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    @ResponseBody
    public String addStudent(@RequestParam String name, int courseId) {
        studentService.addStudent(name, courseId);
        return "Success";
    }

    @PostMapping("/dropStudent")
    @ResponseBody
    public String dropStudent(@RequestParam int id) {
        studentService.removeStudent(id);
        return "Success";
    }

    @PostMapping("/rateStudent")
    @ResponseBody
    public String rateStudent(@RequestParam int id, int mark) {
        studentService.rateStudent(id, mark);
        return "Success";
    }

    @GetMapping("/getEduTimeLeft")
    @ResponseBody
    public int getEduTimeLeft(@RequestParam int id) {
        return studentService.getEduTimeLeft(id);
    }

    @GetMapping("/getDropChance")
    @ResponseBody
    public String getDropChance(@RequestParam int id) {
        return studentService.getDropChance(id);
    }

    @GetMapping("/getReport")
    @ResponseBody
    public String getReport(@RequestParam int id) {
        return studentService.getReportStudent(id);
    }

    @GetMapping("/getAllReport")
    @ResponseBody
    public String getAllReport(@RequestParam int sort, int order, int filter) {
        return studentService.getAllReport(sort, order, filter).toString();
    }

    @GetMapping("/AmIRunning")
    @ResponseBody
    public String test() {
        return "I am running";
    }
}