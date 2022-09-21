package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // returns list to find all students in the database
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        // To avoid NullPointerExceptions we can use Optionals;
        // With Optionals one can specify alternate values to return or alternate code to run. This makes code more readable
        // Optionals are container objects which may or may not contain a non-null value
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        // If value isPresent(), one will return true and get() will return the value; in this case we save the value
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already taken!");
        }
        studentRepository.save(student);
    }

    // Transactional belongs to the Service Layer of an app;
    // One shouldn't use it in the Web Layer because it can increase the DB transaction response time
    // and make it more difficult to provide the right error message for a given DB transaction error
    // We use @Transactional if we have to parse a given statement, build a report or save results to the DB
    // Read more about @Transactional here: https://vladmihalcea.com/spring-transactional-annotation/
    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email) {
        // Check first if student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                "Student with Id " + studentId + " does not exist."
        ));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("Email already taken.");
            }
            student.setEmail(email);
        }
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }
}
