package fr.efrei.studentserver.web.rest;

import fr.efrei.studentserver.domain.Student;
import fr.efrei.studentserver.repo.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class StudentResourceIT {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Transactional
    void createStudent() {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        System.out.println("student : " + studentRepository.findAll());
        assertThat(databaseSizeBeforeCreate).isEqualTo(6);

        Student student = new Student();
        student.setName("nathan");
        studentRepository.save(student);

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void updateStudent() {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        assertThat(databaseSizeBeforeUpdate).isEqualTo(6);

        Student student = studentRepository.findById(6).get();
        student.setName("nathan");
        studentRepository.save(student);

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudent() {
        int databaseSizeBeforeDelete = studentRepository.findAll().size();
        assertThat(databaseSizeBeforeDelete).isEqualTo(6);

        studentRepository.deleteById(11);

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);

    }

    @Test
    @Transactional
    void getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(6);
    }
}
