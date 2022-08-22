package com.daniloewerton.com.school.repositories;

import com.daniloewerton.com.school.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseById(Long id);

    Course findCourseByName(String name);

}
