package com.daniloewerton.com.school.repositories;

import com.daniloewerton.com.school.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findTeacherByCpf(String cpf);
}
