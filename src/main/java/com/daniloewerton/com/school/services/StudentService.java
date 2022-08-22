package com.daniloewerton.com.school.services;

import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.dto.StudentEnrollDTO;

import java.util.List;

public interface StudentService {

    Student enroll(StudentEnrollDTO studentDTO);

    List<Student> findStudentByCourse(Long id);

    Student update(StudentEnrollDTO studentEnrollDTO);

    void delete(Long id);

    void findByCpf(String cpf);

    Student findById(Long id);

}
