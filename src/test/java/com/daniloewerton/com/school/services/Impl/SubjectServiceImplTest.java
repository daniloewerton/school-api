package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.*;
import com.daniloewerton.com.school.model.dto.SubjectDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.repositories.SubjectRepository;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubjectServiceImplTest {

    public static final long ID = 1L;
    public static final String SUBJECT_NAME = "Informática 1";
    public static final String COURSE_NAME = "Computer Science";
    public static final int WORKLOAD = 12345;
    public static final double COURSE_VALUE = 4.785;
    public static final LocalDate DATE = LocalDate.parse("2020-05-10");
    public static final double MINIMUM_GRADE = 7.0;
    public static final double MAXIMUM_GRADE = 10.0;
    public static final int VACANCY = 10;
    public static final long TEACHER_ID = 2L;
    public static final long SUBJECT_ID = 3L;
    public static final Status STATUS = Status.ACTIVE;
    public static final String NAME = "João";
    public static final String CPF = "02112335474";
    public static final String EMAIL = "email@email.com";

    @InjectMocks
    private SubjectServiceImpl service;

    @Mock
    private SubjectRepository repository;
    @Mock
    private CourseServiceImpl courseService;
    @Mock
    private SchoolClassRepository schoolClassRepository;
    @Mock
    private ModelMapper mapper;

    private Subject subject;
    private Course course;
    private SubjectDTO subjectDTO;
    private Teacher teacher;
    private Student student;
    private List<Subject> subjectList;
    private Set<Student> studentSet;
    private SchoolClass schoolClass;
    private List<SchoolClass> schoolClassList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenInsertThenReturnAnInstanceOfSubject() {
        when(repository.save(any())).thenReturn(subject);
        Subject response = service.insert(subjectDTO);

        assertEquals(response.getClass(), Subject.class);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), SUBJECT_NAME);
    }

    @Test
    void whenUpdateThenReturnAnInstanceOfSubject() {
        when(repository.save(any())).thenReturn(subject);
        when(repository.findById(anyLong())).thenReturn(Optional.of(subject));

        Subject response = service.update(subjectDTO);
        assertEquals(response.getClass(), Subject.class);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), SUBJECT_NAME);
    }

    @Test
    void whenUpdateThenThrowObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Disciplina não encontrada."));

        try {
            service.update(subjectDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(ex.getMessage(), "Disciplina não encontrada.");
        }
    }

    @Test
    void whenListSubjectByCourseThenReturnAListOfSubject() {
        when(courseService.findById(anyLong())).thenReturn(course);
        when(schoolClassRepository.findAll()).thenReturn(List.of(schoolClass));

        List<Subject> response = service.listSubjectByCourse(anyLong());

        assertNotNull(response);
        assertEquals(response.getClass(), ArrayList.class);
        assertEquals(response.get(0).getName(), SUBJECT_NAME);
        assertEquals(response.get(0).getId(), ID);
    }

    void start() {
        subject = new Subject(ID, SUBJECT_NAME);
        course = new Course(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        subjectDTO = new SubjectDTO(ID, SUBJECT_NAME);
        teacher = new Teacher(ID, NAME, CPF);
        student = new Student(ID, NAME, CPF, DATE, EMAIL, course, Status.ACTIVE);
        studentSet = Set.of(student);
        schoolClass = new SchoolClass(ID, DATE, VACANCY, course, teacher, subject, studentSet, Status.ACTIVE);
        subjectList = List.of(subject);
        schoolClassList = List.of(schoolClass);
    }
}