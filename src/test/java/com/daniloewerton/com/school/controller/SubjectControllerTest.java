package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.Subject;
import com.daniloewerton.com.school.model.dto.SubjectDTO;
import com.daniloewerton.com.school.services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SubjectControllerTest {

    public static final long ID = 1L;
    public static final String SUBJECT_NAME = "Informática I";
    @InjectMocks
    private SubjectController controller;

    @Mock
    private SubjectService service;

    @Mock
    private ModelMapper mapper;

    private Subject subject;
    private SubjectDTO subjectDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenInsertThenReturnStatusOk() {
        when(service.insert(subjectDTO)).thenReturn(subject);
        when(mapper.map(any(), any())).thenReturn(subjectDTO);

        ResponseEntity<SubjectDTO> response = controller.insert(subjectDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SubjectDTO.class, response.getBody().getClass());
    }

    @Test
    void whenUpdateThenReturnStatusOk() {
        when(service.update(any())).thenReturn(subject);
        when(mapper.map(any(), any())).thenReturn(subjectDTO);

        ResponseEntity<SubjectDTO> response = controller.update(subjectDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SubjectDTO.class, response.getBody().getClass());
    }

    @Test
    void whenGetSubjectByCourseThenReturnStatusOk() {
        when(service.listSubjectByCourse(Mockito.anyLong())).thenReturn(List.of(subject));
        when(mapper.map(any(), any())).thenReturn(subjectDTO);

        ResponseEntity<List<SubjectDTO>> response = controller.getSubjectByCourse(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(SubjectDTO.class, response.getBody().get(0).getClass());
    }

    void start() {
        subject = new Subject(ID, SUBJECT_NAME);
        subjectDTO = new SubjectDTO(ID, SUBJECT_NAME);
    }
}