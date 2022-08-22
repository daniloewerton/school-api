package com.daniloewerton.com.school.services;

import com.daniloewerton.com.school.model.Course;
import com.daniloewerton.com.school.model.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    Course save(CourseDTO course);

    List<Course> findAll();

    Course update(CourseDTO course);

    void delete(Long id);

    void deleteSchoolClass(Long idSchoolClass, Long idCourse);

    Course findById(Long id);
}
