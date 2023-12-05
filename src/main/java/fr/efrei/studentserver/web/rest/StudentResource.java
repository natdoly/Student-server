package fr.efrei.studentserver.web.rest;

import fr.efrei.studentserver.domain.Student;
import fr.efrei.studentserver.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentResource {
    public final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }
}
