package space.irsi7.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.irsi7.interfaces.StudentService;
import space.irsi7.models.StudentRest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class StudentNotFoundException extends RuntimeException {
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addStudent(@RequestParam String name, int courseId, int groupId) {
        if(studentService.addStudent(name, courseId, groupId)){
            return new ResponseEntity<>(
                    "Student added",
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    "Something went wrong",
                    HttpStatus.CONFLICT
            );
        }
    }

    @PostMapping("/drop/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> dropStudent(@PathVariable int id) {
        if(studentService.removeStudent(id)){
            return new ResponseEntity<>(
                    "Student removed",
                    HttpStatus.NO_CONTENT
            );
        } else {
            return new ResponseEntity<>(
                    "Something went wrong",
                    HttpStatus.CONFLICT
            );
        }
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<String> rateStudent(@PathVariable int id, @RequestParam int mark, int testId) {
        if(studentService.rateStudent(id, mark, testId)){
            return new ResponseEntity<>(
                    "Mark added",
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    "Something went wrong",
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping("/EduTimeLeft/{id}")
    public ResponseEntity<Integer> getEduTimeLeft(@PathVariable int id) {
        Integer timeLeft = studentService.getEduTimeLeft(id).orElseThrow(StudentNotFoundException::new);
        return new ResponseEntity<>(
                timeLeft,
                HttpStatus.OK
        );
    }

    @GetMapping("/DropChance/{id}")
    public ResponseEntity<String> getDropChance(@PathVariable int id) {
        return new ResponseEntity<>(
                studentService.getDropChance(id).orElseThrow(StudentNotFoundException::new),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentRest> getReport(@PathVariable int id) {
        var student = studentService.getReport(id).orElseThrow(StudentNotFoundException::new);
        return new ResponseEntity<>(
                student,
                HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentRest>> getAllReport(@RequestParam int sort, int order, int filter) {
        return new ResponseEntity<>(
                studentService.getAllReport(sort,order, filter),
                HttpStatus.OK
        );
    }

    @GetMapping("/AmIRunning")
    public String test() {
        return "I am running";
    }
}