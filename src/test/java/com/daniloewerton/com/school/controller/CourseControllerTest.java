package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.Course;
import com.daniloewerton.com.school.model.dto.CourseDTO;
import com.daniloewerton.com.school.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseControllerTest {

    @InjectMocks
    private CourseController controller;

    @Mock
    private CourseService service;

    @Mock
    private ModelMapper mapper;

    private Course course;
    private CourseDTO courseDTO;

    public static final long ID = 1L;
    public static final String COURSE_NAME = "Computer Science";
    public static final int WORKLOAD = 12345;
    public static final double COURSE_VALUE = 4.785;
    public static final double MINIMUM_GRADE = 7.0;
    public static final double MAXIMUM_GRADE = 10.0;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenSaveThenReturnStatusOk() {
        when(service.save(any())).thenReturn(course);

        ResponseEntity<CourseDTO> response = controller.save(courseDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenFindAllThenReturnAListOfCourseDTO() {
        when(service.findAll()).thenReturn(List.of(course));

        ResponseEntity<List<CourseDTO>> response = controller.findAll();

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUpdateThenReturnStatusOk() {
        when(service.update(any())).thenReturn(course);
        when(mapper.map(any(), any())).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = controller.update(ID, courseDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CourseDTO.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(COURSE_NAME, response.getBody().getName());
        assertEquals(WORKLOAD, response.getBody().getWorkload());
        assertEquals(COURSE_VALUE, response.getBody().getCourseValue());
        assertEquals(MINIMUM_GRADE, response.getBody().getMinimumGrade());
        assertEquals(MAXIMUM_GRADE, response.getBody().getMaximumGrade());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyLong());

        ResponseEntity<CourseDTO> response = controller.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
    }

    @Test
    void whenDeleteSchoolClassThenReturnSuccess() {
        doNothing().when(service).deleteSchoolClass(anyLong(), anyLong());

        ResponseEntity<CourseDTO> response = controller.deleteSchoolClass(ID, ID);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(service, times(1)).deleteSchoolClass(anyLong(), anyLong());
    }

    void start() {
        course = new Course(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        courseDTO = new CourseDTO(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
    }
}