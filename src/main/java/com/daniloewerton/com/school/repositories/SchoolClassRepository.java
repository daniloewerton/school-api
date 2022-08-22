package com.daniloewerton.com.school.repositories;

import com.daniloewerton.com.school.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    List<SchoolClass> findClassByTeacherId(Long id);

    List<SchoolClass> findClassByBeginDate(LocalDate date);

}
