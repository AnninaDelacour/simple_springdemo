package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // this interface is responsible for data access

// fetching from database
public interface StudentRepository extends JpaRepository<Student, Long> {
    // custom function to find user by email

    // getting via: SELECT * FROM student WHERE email = ?
    // or using Annotations: @Query
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
