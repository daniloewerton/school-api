package com.daniloewerton.com.school.services;

import com.daniloewerton.com.school.model.SchoolClass;
import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.dto.SchoolClassDTO;

import java.util.List;

public interface SchoolClassService {

    SchoolClass insert(SchoolClassDTO schoolClassDTO);

    SchoolClass update(SchoolClassDTO schoolClassDTO);

    SchoolClass enrollStudent(Long idStudent, Long idSubject, Long idSchoolClass);

    List<SchoolClass> getPerDate(String date);

    void finishClass(Long id);

    SchoolClass findById(Long id);

    void checkVacancy(SchoolClass schoolClass);

    void checkStudentEnrollment(SchoolClass schoolClass, Student student);

    void checkClassStatus(SchoolClass schoolClass);
}