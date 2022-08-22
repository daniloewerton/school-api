package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.Course;
import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.dto.StudentDTO;
import com.daniloewerton.com.school.model.dto.StudentEnrollDTO;
import com.daniloewerton.com.school.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentControllerTest {

    @InjectMocks
    private StudentController controller;

    @Mock
    private StudentService service;
    @Mock
    private ModelMapper mapper;

    private Student student;
    private StudentDTO studentDTO;
    private StudentEnrollDTO studentEnrollDTO;
    private Course course;

    public static final String COURSE_NAME = "Computer Science";
    public static final int WORKLOAD = 12345;
    public static final double COURSE_VALUE = 4.785;
    public static final LocalDate DATE = LocalDate.parse("2020-05-10");
    public static final double MINIMUM_GRADE = 7.0;
    public static final double MAXIMUM_GRADE = 10.0;
    public static final String CPF = "12345678910";
    public static final String EMAIL = "danilo@email.com";
    public static final Status STATUS = Status.ACTIVE;
    public static final long ID = 1L;
    public static final String NAME = "Danilo Ewerton";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenEnrollThenReturnStatusOk() {
        when(service.enroll(any())).thenReturn(student);
        when(mapper.map(any(), any())).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = controller.enroll(studentEnrollDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(StudentDTO.class, response.getBody().getClass());
    }

    @Test
    void whenUpdateThenReturnStatusOk() {
        when(service.update(any())).thenReturn(student);
        when(mapper.map(any(), any())).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = controller.update(studentEnrollDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StudentDTO.class, response.getBody().getClass());
    }

    @Test
    void whenFindStudentByCourseThenReturnStatusOk() {
        when(service.findStudentByCourse(anyLong())).thenReturn(List.of(student));

        ResponseEntity<List<StudentDTO>> response = controller.findStudentByCourse(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyLong());

        ResponseEntity<StudentDTO> response = controller.delete(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
    }

    void start() {
        student = new Student(ID, NAME, CPF, DATE, EMAIL, course, STATUS);
        course = new Course(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        studentDTO = new StudentDTO(ID, NAME, CPF, DATE, EMAIL, COURSE_NAME, ID, course, STATUS);
        studentEnrollDTO = new StudentEnrollDTO(ID, NAME, CPF, DATE, EMAIL, COURSE_NAME, ID, course, STATUS);
    }
}