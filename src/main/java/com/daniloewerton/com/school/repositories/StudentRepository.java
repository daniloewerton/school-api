package com.daniloewerton.com.school.repositories;

import com.daniloewerton.com.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentById(Long id);

    @Query(value = "SELECT S FROM Student as S WHERE course_id = ?1")
    Set<Student> findAllStudentsByCourse(Long course_id);

    Student findStudentByCpf(String cpf);
}
