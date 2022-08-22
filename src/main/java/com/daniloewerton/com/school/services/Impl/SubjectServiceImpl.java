package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.model.Course;
import com.daniloewerton.com.school.model.SchoolClass;
import com.daniloewerton.com.school.model.Subject;
import com.daniloewerton.com.school.model.dto.SubjectDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.repositories.SubjectRepository;
import com.daniloewerton.com.school.services.CourseService;
import com.daniloewerton.com.school.services.SubjectService;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository repository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Subject insert(SubjectDTO subjectDTO) {
        return repository.save(mapper.map(subjectDTO, Subject.class));
    }

    @Override
    public Subject update(SubjectDTO subjectDTO) {
        findById(subjectDTO.getId());
        return repository.save(mapper.map(subjectDTO, Subject.class));
    }

    @Override
    public List<Subject> listSubjectByCourse(Long idCourse) {
        Course course = courseService.findById(idCourse);
        List<SchoolClass> schoolClass = schoolClassRepository.findAll();
        List<Subject> subjectsList = new ArrayList<>();

        for (SchoolClass sc : schoolClass) {
            if (sc.getCourse() == course) {
                subjectsList.add(sc.getSubject());
            }
        }
        return subjectsList;
    }

    @Override
    public Subject findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Disciplina não encontrada."));
    }
}