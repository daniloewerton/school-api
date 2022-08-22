package com.daniloewerton.com.school.services;

import com.daniloewerton.com.school.model.Teacher;
import com.daniloewerton.com.school.model.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {

    Teacher enroll(TeacherDTO teacherDTO);

    Teacher update(TeacherDTO teacherDTO);

    List<Teacher> findAll();

    void delete(Long id);

    Teacher findById(Long id);

    void findByCpf(String cpf);

    void verifyOcupation(Long id);
}
