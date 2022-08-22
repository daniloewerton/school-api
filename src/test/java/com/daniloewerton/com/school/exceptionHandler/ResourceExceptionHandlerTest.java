package com.daniloewerton.com.school.exceptionHandler;

import com.daniloewerton.com.school.services.exception.IllegalArgumentException;
import com.daniloewerton.com.school.services.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ResourceExceptionHandlerTest {

    public static final String OBJECT_NOT_FOUND = "Objeto não encontrado.";
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundThenReturnAResponseEntityStatus404() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(
                new ObjectNotFoundException(OBJECT_NOT_FOUND),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(OBJECT_NOT_FOUND, response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenObjectAlreadyExistsThenReturnResponseEntityStatus400() {
        ResponseEntity<StandardError> response = exceptionHandler.objectAlreadyExists(
                new ObjectAlreadyExists("Objeto existente."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Objeto existente.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenStudentAlreadyEnrolledThenReturnResponseEntity400() {
        ResponseEntity<StandardError> response = exceptionHandler.studentAlreadyEnrolled(
                new StudentAlreadyEnrolled("Estudante matriculado."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Estudante matriculado.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenTypeUnexpectedExceptionThenReturnResponseEntityStauts400() {
        ResponseEntity<StandardError> response = exceptionHandler.typeUnexpectedException(
                new TypeUnexpectedException("Tipo inesperado."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Tipo inesperado.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenIllegalCpfStatementThenReturnResponseEntityStatus400() {
        ResponseEntity<StandardError> response = exceptionHandler.illegalCpfStatement(
                new IllegalCpfStatementException("Cpf Inválido."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cpf Inválido.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenNoVacancyExceptionThenReturnResponseEntityStatus400() {
        ResponseEntity<StandardError> response = exceptionHandler.noVacancyException(
                new SchoolClassNoVacancyException("Não há vagas."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Não há vagas.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenFinishedClassExceptionThenReturnResponseEntityStatus400() {
        ResponseEntity<StandardError> response = exceptionHandler.finishedClassException(
                new FinishedSchoolClassException("Turma encerrada."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Turma encerrada.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenDataIntegrityViolationExceptionThenReturnResponseEntityStatus400() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolationException(
                new DataIntegrityViolation("Professor com turma ativa."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Professor com turma ativa.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }

    @Test
    void whenIllegalArgumentExceptionThenReturnResponseEntityStatus400() {
        ResponseEntity<StandardError> response = exceptionHandler.illegalArgumentException(
                new IllegalArgumentException("Status incorreto. Utilizar ACTIVE ou FINISHED para operações em turmas."),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Status incorreto. Utilizar ACTIVE ou FINISHED para operações em turmas.", response.getBody().getError());
        assertEquals(StandardError.class, response.getBody().getClass());
    }
}