package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.Teacher;
import com.daniloewerton.com.school.model.dto.TeacherDTO;
import com.daniloewerton.com.school.services.TeacherService;
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
class TeacherControllerTest {

    public static final long ID = 1L;
    public static final String NAME = "João";
    public static final String CPF = "02547851220";
    @InjectMocks
    private TeacherController controller;

    @Mock
    private TeacherService service;

    @Mock
    private ModelMapper mapper;

    private Teacher teacher;
    private TeacherDTO teacherDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenEnrollThenReturnStatusOk() {
        when(service.enroll(any())).thenReturn(teacher);

        ResponseEntity<TeacherDTO> response = controller.enroll(teacherDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUpdateThenReturnStatusOk() {
        when(service.update(any())).thenReturn(teacher);

        ResponseEntity<TeacherDTO> response = controller.update(teacherDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenFindAllThenReturnStatusOk() {
        when(service.findAll()).thenReturn(List.of(teacher));

        ResponseEntity<List<TeacherDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyLong());

        ResponseEntity<TeacherDTO> response = controller.delete(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
    }

    void start() {
        teacher = new Teacher(ID, NAME, CPF);
        teacherDTO = new TeacherDTO(ID, NAME, CPF);
    }
}