package fr.efrei.studentserver.web.rest;

import fr.efrei.studentserver.domain.Student;
import fr.efrei.studentserver.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Integer id) {
        Optional<Student> student = studentService.findById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }

    @PostMapping("/students")
    public ResponseEntity<?> createStudent(@RequestParam(name = "name") String name,
                                           @RequestParam(name = "age") Integer age) {
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setAge(age);

        Student createdStudent = studentService.save(newStudent);
        if (createdStudent != null) {
            return ResponseEntity.ok(studentService.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not created because of missing parameters");
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id,
                                           @RequestParam(name = "name") String name,
                                           @RequestParam(name = "age") Integer age) {

        Optional<Student> student = studentService.findById(id);

        if (student.isPresent()) {
            Student updateStudent = student.get();
            updateStudent.setName(name);
            updateStudent.setAge(age);
            studentService.save(updateStudent);
            return ResponseEntity.ok(studentService.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        Optional<Student> student = studentService.findById(id);

        if (student.isPresent()) {
            studentService.deleteById(id);
            return ResponseEntity.ok(studentService.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }
}
