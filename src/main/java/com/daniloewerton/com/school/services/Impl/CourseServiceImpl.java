package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.model.Course;
import com.daniloewerton.com.school.model.SchoolClass;
import com.daniloewerton.com.school.model.dto.CourseDTO;
import com.daniloewerton.com.school.repositories.CourseRepository;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.services.CourseService;
import com.daniloewerton.com.school.services.exception.ObjectAlreadyExists;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Course save(CourseDTO objCourse) {
        findByName(objCourse.getName());
        return repository.save(mapper.map(objCourse, Course.class));
    }

    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public Course update(CourseDTO courseDTO) {
        findById(courseDTO.getId());
        return repository.save(mapper.map(courseDTO, Course.class));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    @Override
    public void deleteSchoolClass(Long idSchoolClass, Long idCourse) {
        SchoolClass schoolClass = schoolClassRepository.findById(idSchoolClass).get(); //Tentar tratar
        Course course = repository.findCourseById(idCourse);

        if (schoolClass.getCourse().equals(course)) {
            schoolClassRepository.deleteById(idSchoolClass);
        } else {
            throw new ObjectNotFoundException("Turma não encontrada.");
        }
    }

    @Override
    public Course findById(Long id) {
        Optional<Course> course = repository.findById(id);
        return course.orElseThrow(() -> new ObjectNotFoundException("Curso não encontrado."));
    }

    public void findByName(String name) {
        Course course = repository.findCourseByName(name);
        if(course != null) {
            throw new ObjectAlreadyExists("Curso já cadastrado.");
        }
    }
}