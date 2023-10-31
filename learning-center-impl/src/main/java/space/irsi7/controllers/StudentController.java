package space.irsi7.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import space.irsi7.interfaces.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public void addStudent(@RequestParam String name, int courseId) {
        studentService.addStudent(name, courseId);
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

    //PathVariable
    @GetMapping("/getEduTimeLeft")
    @ResponseBody
    public int getEduTimeLeft(@RequestParam int id) {
        return studentService.getEduTimeLeft(id);
    }

    //PathVariable
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