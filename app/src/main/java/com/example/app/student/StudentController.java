package com.example.app.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{student_id}")
    public void deleteStudent(@PathVariable("student_id") Long student_id){
        studentService.deleteStudent(student_id);
    }

    @PutMapping(path = "{student_id}")
    public void updateStudent(
            @PathVariable("student_id") Long studentId,
            @RequestParam(required=false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId,name,email);

    }

}
