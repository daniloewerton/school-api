package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.SchoolClass;
import com.daniloewerton.com.school.model.Teacher;
import com.daniloewerton.com.school.model.dto.TeacherDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.repositories.TeacherRepository;
import com.daniloewerton.com.school.services.TeacherService;
import com.daniloewerton.com.school.services.exception.DataIntegrityViolation;
import com.daniloewerton.com.school.services.exception.ObjectAlreadyExists;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl extends GenericsOperationsImpl implements TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Teacher enroll(TeacherDTO teacherDTO) {
        findByCpf(teacherDTO.getCpf());
        teacherDTO.setCpf(validatesCpf(teacherDTO.getCpf()));
        return repository.save(mapper.map(teacherDTO, Teacher.class));
    }

    @Override
    public Teacher update(TeacherDTO teacherDTO) {
        findById(teacherDTO.getId());
        findByCpf(teacherDTO.getCpf());
        teacherDTO.setCpf(validatesCpf(teacherDTO.getCpf()));
        return repository.save(mapper.map(teacherDTO, Teacher.class));
    }

    @Override
    public List<Teacher> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        findById(id);
        verifyOcupation(id);
        repository.deleteById(id);
    }

    @Override
    public Teacher findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Professor não encontrado."));
    }

    public void findByCpf(String cpf) {
        Teacher teacher = repository.findTeacherByCpf(validatesCpf(cpf));
        if(teacher != null) {
            throw new ObjectAlreadyExists("CPF já cadastrado na instituição.");
        }
    }

    @Override
    public void verifyOcupation(Long id) {
        List<SchoolClass> schoolClasses = schoolClassRepository.findClassByTeacherId(id);

        for (SchoolClass sc : schoolClasses) {
            if (sc.getStatus() != Status.FINISHED) {
                throw new DataIntegrityViolation("Professor com turma ativa.");
            }
        }
    }
}