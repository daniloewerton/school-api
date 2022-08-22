package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.*;
import com.daniloewerton.com.school.model.dto.CourseDTO;
import com.daniloewerton.com.school.repositories.CourseRepository;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.services.exception.ObjectAlreadyExists;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseServiceImplTest {

    public static final long ID = 1L;
    public static final String COURSE_NAME = "Computer Science";
    public static final int WORKLOAD = 12345;
    public static final double COURSE_VALUE = 4.785;
    public static final LocalDate DATE = LocalDate.parse("2020-05-10");
    public static final double MINIMUM_GRADE = 7.0;
    public static final double MAXIMUM_GRADE = 10.0;
    public static final String CURSO_JA_CADASTRADO = "Curso já cadastrado.";
    public static final String COURSE_NOT_FOUND = "Curso não encontrado.";
    public static final String NAME = "João";
    public static final String CPF = "256.857.489-74";
    public static final String EMAIL = "email@email.com";
    public static final String SUBJECT_NAME = "Informática 1";
    public static final int VACANCY = 10;

    @InjectMocks
    private CourseServiceImpl service;
    @Mock
    private ModelMapper mapper;
    @Mock
    private CourseRepository repository;
    @Mock
    private SchoolClassRepository schoolClassRepository;

    private Course course;
    private CourseDTO courseDTO;
    private SchoolClass schoolClass;
    private Teacher teacher;
    private Subject subject;
    private Optional<Course> optionalCourse;
    private Optional<SchoolClass> optionalSchoolClass;
    private List<Course> listCourse;
    private Set<Student> students;
    private Status status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenSaveThenReturnAnInstanceOfCourse() {
        when(repository.save(any())).thenReturn(course);

        Course response = service.save(courseDTO);

        assertNotNull(response);
        assertEquals(Course.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(COURSE_NAME, response.getName());
        assertEquals(WORKLOAD, response.getWorkload());
        assertEquals(COURSE_VALUE, response.getCourseValue());
        assertEquals(MINIMUM_GRADE, response.getMinimumGrade());
        assertEquals(MAXIMUM_GRADE, response.getMaximumGrade());
    }

    @Test
    void whenSaveThenReturnAnObjectAlreadyExists() {
        when(repository.save(any())).thenThrow(new ObjectAlreadyExists(CURSO_JA_CADASTRADO));
        try {
            service.save(courseDTO);
        } catch(Exception ex) {
            assertEquals(ObjectAlreadyExists.class, ex.getClass());
            assertEquals(CURSO_JA_CADASTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAListOfCourses() {
        when(repository.findAll()).thenReturn(listCourse);

        List<Course> response = service.findAll();

        assertEquals(1, response.size());
        assertEquals(Course.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
        assertEquals(COURSE_NAME, response.get(0).getName());
        assertEquals(WORKLOAD, response.get(0).getWorkload());
        assertEquals(COURSE_VALUE, response.get(0).getCourseValue());
        assertEquals(MINIMUM_GRADE, response.get(0).getMinimumGrade());
        assertEquals(MAXIMUM_GRADE, response.get(0).getMaximumGrade());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(course);
        when(repository.findById(anyLong())).thenReturn(optionalCourse);

        Course response = service.update(courseDTO);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(COURSE_NAME, response.getName());
        assertEquals(WORKLOAD, response.getWorkload());
        assertEquals(COURSE_VALUE, response.getCourseValue());
        assertEquals(MINIMUM_GRADE, response.getMinimumGrade());
        assertEquals(MAXIMUM_GRADE, response.getMaximumGrade());
    }

    @Test
    void whenUpdateThenReturnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException(COURSE_NOT_FOUND));

        try {
            service.delete(ID);
        } catch(Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(COURSE_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        when(repository.findById(anyLong())).thenReturn(optionalCourse);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(ID);
        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException(COURSE_NOT_FOUND));

        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(COURSE_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenDeleteSchoolClassThenReturnSuccess() {
        when(schoolClassRepository.findById(anyLong())).thenReturn(optionalSchoolClass);
        when(repository.findCourseById(anyLong())).thenReturn(course);

        doNothing().when(schoolClassRepository).deleteById(anyLong());
        service.deleteSchoolClass(ID, ID);
        verify(schoolClassRepository, times(1)).deleteById(ID);
    }

    @Test
    void whenDeleteSchoolClassThenThrowObjectNotFoundException() {
        when(schoolClassRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException(COURSE_NOT_FOUND));

        try {
            service.deleteSchoolClass(ID, ID);
        } catch (Exception ex) {
            assertEquals(ex.getMessage(), COURSE_NOT_FOUND);
            assertEquals(ObjectNotFoundException.class, ex.getClass());
        }
    }

    void start() {
        course = new Course(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        courseDTO = new CourseDTO(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        schoolClass = new SchoolClass(ID, DATE, VACANCY, course, teacher, subject, students, status);
        optionalCourse = Optional.of(course);
        optionalSchoolClass = Optional.of(schoolClass);
        listCourse = List.of(course);
    }
}