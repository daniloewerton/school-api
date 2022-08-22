package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.*;
import com.daniloewerton.com.school.model.dto.SchoolClassDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.services.SchoolClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class SchoolClassControllerTest {

    @InjectMocks
    private SchoolClassController controller;

    @Mock
    private SchoolClassService service;

    @Mock
    private ModelMapper mapper;

    @Mock
    private SchoolClassRepository repository;

    private SchoolClass schoolClass;
    private SchoolClassDTO schoolClassDTO;
    private Subject subject;
    private Course course;
    private Teacher teacher;
    private Student student;
    private Set<Student> studentSet;

    public static final long ID = 1L;
    public static final String SUBJECT_NAME = "Informática 1";
    public static final String COURSE_NAME = "Computer Science";
    public static final int WORKLOAD = 12345;
    public static final double COURSE_VALUE = 4.785;
    public static final LocalDate DATE = LocalDate.parse("2020-05-10");
    public static final double MINIMUM_GRADE = 7.0;
    public static final double MAXIMUM_GRADE = 10.0;
    public static final int VACANCY = 10;
    public static final String NAME = "João";
    public static final String CPF = "02112335474";
    public static final String EMAIL = "email@email.com";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenInsertThenReturnStatusOk() {
        when(service.insert(any())).thenReturn(schoolClass);
        when(mapper.map(any(), any())).thenReturn(schoolClassDTO);

        ResponseEntity<SchoolClassDTO> response = controller.insert(schoolClassDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(SchoolClassDTO.class, response.getBody().getClass());
    }

    @Test
    void whenUpdateThenReturnStatusOk() {
        when(service.update(any())).thenReturn(schoolClass);
        when(mapper.map(any(), any())).thenReturn(schoolClassDTO);

        ResponseEntity<SchoolClassDTO> response = controller.update(schoolClassDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(SchoolClassDTO.class, response.getBody().getClass());
    }

    @Test
    void whenEnrollStudentThenReturnSuccess() {
        when(service.enrollStudent(anyLong(), anyLong(), anyLong())).thenReturn(schoolClass);
        when(mapper.map(any(), any())).thenReturn(schoolClassDTO);

        schoolClass.setStudents(new HashSet<>());
        ResponseEntity<SchoolClassDTO> response = controller.enrollStudent(ID, ID, ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(SchoolClassDTO.class, response.getBody().getClass());
    }

    @Test
    void whenFinishClassThenReturnNoContent() {
        doNothing().when(service).finishClass(anyLong());
        controller.finishClass(ID);
        verify(service, times(1)).finishClass(anyLong());
    }

    @Test
    void whenGetPerDateThenReturnAListOfSchoolClass() {
        when(service.getPerDate(any())).thenReturn(List.of(schoolClass));
        when(mapper.map(any(), any())).thenReturn(schoolClassDTO);

        ResponseEntity<List<SchoolClassDTO>> response = controller.getPerDate(DATE.toString());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(SchoolClassDTO.class, response.getBody().get(0).getClass());
    }

    void start() {
        subject = new Subject(ID, SUBJECT_NAME);
        course = new Course(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        teacher = new Teacher(ID, NAME, CPF);
        student = new Student(ID, NAME, CPF, DATE, EMAIL, course, Status.ACTIVE);
        studentSet = Set.of(student);
        schoolClass = new SchoolClass(ID, DATE, VACANCY, course, teacher, subject, studentSet, Status.ACTIVE);
        schoolClassDTO = new SchoolClassDTO(ID, DATE, VACANCY, ID, ID, ID, Status.ACTIVE);
    }
}