package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.dto.StudentDTO;
import com.daniloewerton.com.school.model.dto.StudentEnrollDTO;
import com.daniloewerton.com.school.repositories.StudentRepository;
import com.daniloewerton.com.school.services.CourseService;
import com.daniloewerton.com.school.services.StudentService;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import com.daniloewerton.com.school.services.exception.StudentAlreadyEnrolled;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl extends GenericsOperationsImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Student enroll(StudentEnrollDTO studentEnrollDTO)  {
        StudentDTO dto = new StudentDTO();

        studentEnrollDTO.setCpf(validatesCpf(studentEnrollDTO.getCpf()));
        findByCpf(studentEnrollDTO.getCpf());

        dto.setName(studentEnrollDTO.getName());
        dto.setCpf(studentEnrollDTO.getCpf());
        dto.setEmail(studentEnrollDTO.getEmail());
        dto.setCourseName(studentEnrollDTO.getCourseName());
        dto.setCourseId(studentEnrollDTO.getCourseId());
        dto.setEnrolledCourse(courseService.findById(studentEnrollDTO.getCourseId()));
        dto.setEnrollmentStatus(Status.ACTIVE);
        dto.setBirthdate(studentEnrollDTO.getBirthdate());

        return repository.save(mapper.map(dto, Student.class));
    }

    @Override
    public List<Student> findStudentByCourse(Long id) {
        Set<Student> students = repository.findAllStudentsByCourse(id);
        try {
            students.stream().toList().get(0);
        } catch(Exception ex) {
            throw new ObjectNotFoundException
                    ("Nenhum estudante matriculado no curso.");
        }
        return students.stream()
                .map(x -> mapper.map(x, Student.class))
                .collect(Collectors.toList());
    }

    @Override
    public Student update(StudentEnrollDTO studentEnrollDTO) {
        StudentDTO studentDTO = new StudentDTO();
        Student student = findById(studentEnrollDTO.getId());

        studentDTO.setId(student.getId());
        studentDTO.setName(studentEnrollDTO.getName());
        studentDTO.setCpf(validatesCpf(studentEnrollDTO.getCpf()));
        studentDTO.setBirthdate(studentEnrollDTO.getBirthdate());
        studentDTO.setEmail(studentEnrollDTO.getEmail());
        studentDTO.setCourseName(student.getEnrolledCourse().getName());
        studentDTO.setEnrolledCourse(student.getEnrolledCourse());
        studentDTO.setEnrollmentStatus(studentEnrollDTO.getEnrollmentStatus());

        return repository.save(mapper.map(studentDTO, Student.class));
    }

    @Override
    public void delete(Long id) {
        Student student = findById(id);
        repository.deleteById(student.getId());
    }

    @Override
    public void findByCpf(String cpf) {
        Student student = repository.findStudentByCpf(cpf);
        if(student != null) {
            throw new StudentAlreadyEnrolled("CPF já cadastrado na instituição.");
        }
    }

    @Override
    public Student findById(Long id) {
        Student student = repository.findStudentById(id);
        if (student == null) {
            throw new ObjectNotFoundException("Estudante não encontrado.");
        }
        return student;
    }
}