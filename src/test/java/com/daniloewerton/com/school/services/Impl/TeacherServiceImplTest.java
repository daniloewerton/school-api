package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.model.Teacher;
import com.daniloewerton.com.school.model.dto.TeacherDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.repositories.TeacherRepository;
import com.daniloewerton.com.school.services.exception.DataIntegrityViolation;
import com.daniloewerton.com.school.services.exception.ObjectAlreadyExists;
import com.daniloewerton.com.school.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeacherServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "José Silva";
    public static final String CPF = "28645874110";

    @InjectMocks
    private TeacherServiceImpl service;

    @Mock
    private TeacherRepository repository;

    @Mock
    private ModelMapper mapper;
    @Mock
    private SchoolClassRepository schoolClassRepository;

    private Teacher teacher;
    private TeacherDTO teacherDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenEnrollThenReturnAnInstanceOfTeacher() {
        when(repository.save(any())).thenReturn(teacher);

        Teacher response = service.enroll(teacherDTO);

        assertNotNull(response);
        assertEquals(response.getClass(), Teacher.class);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), NAME);
        assertEquals(response.getCpf(), CPF);
    }

    @Test
    void whenEnrollThenThrowObjectAlreadyExists() {
        when(repository.save(any())).thenThrow(new ObjectAlreadyExists("CPF já cadastrado na instituição."));

        try {
            service.enroll(teacherDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ObjectAlreadyExists.class);
            assertEquals(ex.getMessage(), "CPF já cadastrado na instituição.");
        }
    }

    @Test
    void whenUpdateThenReturnAnInstanceOfTeacher() {
        when(repository.save(any())).thenReturn(teacher);
        when(repository.findById(anyLong())).thenReturn(Optional.of(teacher));

        Teacher response = service.update(teacherDTO);

        assertNotNull(response);
        assertEquals(response.getClass(), Teacher.class);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), NAME);
        assertEquals(response.getCpf(), CPF);
    }

    @Test
    void whenUpdateThenThrowObjectNotFoundException() {
        when(repository.save(any())).thenThrow(new ObjectNotFoundException("Professor não encontrado."));

        try {
            service.update(teacherDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ObjectNotFoundException.class);
            assertEquals(ex.getMessage(), "Professor não encontrado.");
        }
    }

    @Test
    void whenFindAllThenReturnAListOfTeacher() {
        when(repository.findAll()).thenReturn(List.of(teacher));

        List<Teacher> response = service.findAll();

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getClass(), Teacher.class);
        assertEquals(response.get(0).getId(), ID);
        assertEquals(response.get(0).getName(), NAME);
        assertEquals(response.get(0).getCpf(), CPF);
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(teacher));
        doNothing().when(repository).deleteById(anyLong());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowDataIntegrityViolation() {
        when(repository.findById(anyLong())).thenThrow(new DataIntegrityViolation("Professor com turma ativa."));

        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertNotNull(ex);
            assertEquals(ex.getClass(), DataIntegrityViolation.class);
            assertEquals(ex.getMessage(), "Professor com turma ativa.");
        }
    }

    void start() {
        teacher = new Teacher(ID, NAME, CPF);
        teacherDTO = new TeacherDTO(ID, NAME, CPF);
    }
}