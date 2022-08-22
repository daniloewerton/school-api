package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.SchoolClass;
import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.Subject;
import com.daniloewerton.com.school.model.dto.SchoolClassDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.services.*;
import com.daniloewerton.com.school.services.exception.IllegalArgumentException;
import com.daniloewerton.com.school.services.exception.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SchoolClassServiceImpl implements SchoolClassService {

    @Autowired
    private SchoolClassRepository repository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public SchoolClass insert(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass();

        schoolClass.setBeginDate(schoolClassDTO.getBeginDate());
        schoolClass.setVacancy(schoolClassDTO.getVacancy());
        schoolClass.setCourse(courseService.findById(schoolClassDTO.getCourseId()));
        schoolClass.setTeacher(teacherService.findById(schoolClassDTO.getTeacherId()));
        schoolClass.setSubject(subjectService.findById(schoolClassDTO.getSubjectId()));
        schoolClass.setStatus(Status.ACTIVE);

        return repository.save(schoolClass);
    }

    @Override
    public SchoolClass update(SchoolClassDTO schoolClassDTO) {
        findById(schoolClassDTO.getId());
        if (schoolClassDTO.getStatus() != Status.ACTIVE && schoolClassDTO.getStatus() != Status.FINISHED) {
            throw new IllegalArgumentException(
                    "Status incorreto. Utilizar ACTIVE ou FINISHED para operações em turmas.");
        }
        return repository.save(mapper.map(schoolClassDTO, SchoolClass.class));
    }

    @Override
    public SchoolClass enrollStudent(Long idStudent, Long idSubject, Long idSchoolClass) {
        Student student = studentService.findById(idStudent);
        Subject subject = subjectService.findById(idSubject);
        SchoolClass schoolClass = findById(idSchoolClass);

        schoolClass.setSubject(subject);
        checkVacancy(schoolClass);
        checkClassStatus(schoolClass);
        checkStudentEnrollment(schoolClass, student);
        schoolClass.getStudents().add(student);
        return repository.save(schoolClass);
    }

    @Override
    public List<SchoolClass> getPerDate(String date) {
        return repository.findClassByBeginDate(LocalDate.parse(date));
    }

    @Override
    public void finishClass(Long id) {
        SchoolClass schoolClass = repository.findById(id).get();
        schoolClass.setStatus(Status.FINISHED);
    }

    @Override
    public SchoolClass findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Turma não encontrada."));
    }

    @Override
    public void checkVacancy(SchoolClass schoolClass) {
        if(schoolClass.getVacancy() <= 0) {
            throw new SchoolClassNoVacancyException(
                    "Não há vagas disponíveis na turma de número " + schoolClass.getId() +
                            " do curso de " + schoolClass.getCourse().getName());
        }
        schoolClass.setVacancy(schoolClass.getVacancy() - 1);
    }

    @Override
    public void checkStudentEnrollment(SchoolClass schoolClass, Student student) {
        List<Student> studentList = schoolClass.getStudents().stream().toList();

        for (Student st : studentList) {
            if (st.getId().equals(student.getId())) {
                throw new StudentAlreadyEnrolled("Estudante já matriculado na disciplina.");
            }
        }
    }

    @Override
    public void checkClassStatus(SchoolClass schoolClass) {
        if(schoolClass.getStatus() == Status.FINISHED) {
            throw new FinishedSchoolClassException("Não foi possível concluir a matrícula. Turma encerrada.");
        }
    }
}