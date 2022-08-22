package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.Course;
import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.dto.StudentDTO;
import com.daniloewerton.com.school.model.dto.StudentEnrollDTO;
import com.daniloewerton.com.school.repositories.CourseRepository;
import com.daniloewerton.com.school.repositories.StudentRepository;
import com.daniloewerton.com.school.services.CourseService;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import com.daniloewerton.com.school.services.exception.StudentAlreadyEnrolled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceImplTest {

    public static final String COURSE_NAME = "Ciência da Computação";
    public static final String CPF = "12345678910";
    public static final LocalDate BIRTHDATE = LocalDate.parse("1995-04-09");
    public static final String EMAIL = "danilo@email.com";
    public static final Status STATUS = Status.ACTIVE;
    public static final String CPF_ALREADY_EXISTS = "CPF já cadastrado na instituição.";
    public static final long ID = 1L;
    public static final String STUDENT_NOT_ENROLLED = "Nenhum estudante matriculado no curso.";
    public static final String NAME = "Danilo Ewerton";

    @InjectMocks
    private StudentServiceImpl service;
    @Mock
    private CourseService courseService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private StudentRepository repository;
    @Mock
    private CourseRepository courseRepository;

    private Course course;
    private Student student;
    private StudentDTO studentDTO;
    private StudentEnrollDTO studentEnrollDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenEnrollThenReturnAnInstanceOfStudent() {
        when(repository.save(any())).thenReturn(student);
        when(courseRepository.findCourseById(anyLong())).thenReturn(course);

        Student response = service.enroll(studentEnrollDTO);

        assertEquals(Student.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(COURSE_NAME, response.getEnrolledCourse().getName());
        assertEquals(CPF, response.getCpf());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(Course.class, response.getEnrolledCourse().getClass());
        assertEquals(STATUS, response.getEnrollmentStatus());
    }

    @Test
    void whenEnrollThenReturnStudentAlreadyEnrolledException() {
        when(repository.save(any())).thenThrow(new StudentAlreadyEnrolled(CPF_ALREADY_EXISTS));

        try {
            service.enroll(studentEnrollDTO);
        } catch (Exception ex) {
            assertEquals(StudentAlreadyEnrolled.class, ex.getClass());
            assertEquals(CPF_ALREADY_EXISTS, ex.getMessage());
        }
    }

    @Test
    void whenFindStudentByCourseThenReturnAListOfStudents() {
        when(repository.findAllStudentsByCourse(anyLong())).thenReturn(Set.of(student));
        when(mapper.map(any(), any())).thenReturn(student);

        List<Student> response = service.findStudentByCourse(anyLong());

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(response.get(0).getClass(), Student.class);

        assertEquals(ID, response.get(0).getId());
        assertEquals(COURSE_NAME, response.get(0).getEnrolledCourse().getName());
        assertEquals(CPF, response.get(0).getCpf());
        assertEquals(BIRTHDATE, response.get(0).getBirthdate());
        assertEquals(EMAIL, response.get(0).getEmail());
        assertEquals(Course.class, response.get(0).getEnrolledCourse().getClass());
        assertEquals(STATUS, response.get(0).getEnrollmentStatus());
    }

    @Test
    void whenFindStudentByCourseThenThrowAnObjectNotFoundException() {
        when(repository.findAllStudentsByCourse(ID)).thenReturn(Set.of(student));
        when(service.findStudentByCourse(ID)).thenThrow(new ObjectNotFoundException(STUDENT_NOT_ENROLLED));

        try {
            service.findStudentByCourse(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(STUDENT_NOT_ENROLLED, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnAnInstanceOfStudent() {
        when(repository.findStudentById(anyLong())).thenReturn(student);
        when(service.update(studentEnrollDTO)).thenReturn(student);

        Student response = service.update(studentEnrollDTO);

        assertNotNull(response);
        assertEquals(Student.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(COURSE_NAME, response.getEnrolledCourse().getName());
        assertEquals(CPF, response.getCpf());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(STATUS, response.getEnrollmentStatus());
    }

    @Test
    void whenUpdateThenReturnObjectNotFoundException() {
        when(repository.findStudentById(anyLong()))
                .thenThrow(new ObjectNotFoundException(STUDENT_NOT_ENROLLED));

        try {
            service.update(studentEnrollDTO);
        } catch(Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(STUDENT_NOT_ENROLLED, ex.getMessage());
        }
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        when(repository.findStudentById(anyLong())).thenReturn(student);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }

    private void start() {
        course = new Course(ID, COURSE_NAME, 12345, 4.785, 7.0, 10.0);
        student = new Student(ID, NAME, CPF, BIRTHDATE, EMAIL, course, STATUS);
        studentDTO = new StudentDTO(ID, NAME, CPF, BIRTHDATE, EMAIL, COURSE_NAME, ID, course, STATUS);
        studentEnrollDTO = new StudentEnrollDTO(ID, NAME, CPF, BIRTHDATE, EMAIL, COURSE_NAME, ID, course, STATUS);
    }
}