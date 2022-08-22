package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.*;
import com.daniloewerton.com.school.model.dto.SchoolClassDTO;
import com.daniloewerton.com.school.repositories.SchoolClassRepository;
import com.daniloewerton.com.school.repositories.StudentRepository;
import com.daniloewerton.com.school.repositories.SubjectRepository;
import com.daniloewerton.com.school.repositories.TeacherRepository;
import com.daniloewerton.com.school.services.exception.IllegalArgumentException;
import com.daniloewerton.com.school.services.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class SchoolClassServiceImplTest {

    public static final String COURSE_NAME = "Computer Science";
    public static final int WORKLOAD = 12345;
    public static final double COURSE_VALUE = 4.785;
    public static final double MINIMUM_GRADE = 7.0;
    public static final double MAXIMUM_GRADE = 10.0;
    public static final long ID = 1L;
    public static final LocalDate DATE = LocalDate.parse("2022-08-09");
    public static final int VACANCY = 10;
    public static final long TEACHER_ID = 2L;
    public static final long SUBJECT_ID = 3L;
    public static final Status STATUS = Status.ACTIVE;
    public static final String NAME = "João";
    public static final String CPF = "256.857.489-74";
    public static final String EMAIL = "email@email.com";
    public static final String SUBJECT_NAME = "Informática 1";

    @InjectMocks
    private SchoolClassServiceImpl service;

    @Mock
    private CourseServiceImpl courseService;

    @Mock
    private SubjectServiceImpl subjectService;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private SchoolClassRepository repository;

    private Course course;
    private SchoolClassDTO schoolClassDTO;
    private SchoolClass schoolClass;
    private Teacher teacher;
    private Subject subject;
    private Student student;
    private Set<Student> students;
    private List<SchoolClass> schoolClasses;
    private Optional<SchoolClass> optionalSchoolClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenInsertThenReturnAnInstanceOfSchoolClass() {
        when(repository.save(any())).thenReturn(schoolClass);

        SchoolClass response = service.insert(schoolClassDTO);

        assertNotNull(response);
        assertEquals(SchoolClass.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATE, response.getBeginDate());
        assertEquals(VACANCY, response.getVacancy());
        assertEquals(Course.class, response.getCourse().getClass());
        assertEquals(Teacher.class, response.getTeacher().getClass());
        assertEquals(Subject.class, response.getSubject().getClass());
        assertEquals(1, response.getStudents().size());
        assertEquals(STATUS, response.getStatus());
    }

    @Test
    void whenInsertThenReturnObjectNotFoundException() {
        when(repository.save(any())).thenReturn(schoolClass);
        when(courseService.findById(anyLong())).thenThrow(new ObjectNotFoundException("Curso não encontrado."));

        try {
            service.insert(schoolClassDTO);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Curso não encontrado.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.findById(anyLong())).thenReturn(optionalSchoolClass);
        when(repository.save(any())).thenReturn(schoolClass);
        when(mapper.map(any(), any())).thenReturn(schoolClass);

        SchoolClass response = service.update(schoolClassDTO);

        assertNotNull(response);
        assertEquals(SchoolClass.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATE, response.getBeginDate());
        assertEquals(VACANCY, response.getVacancy());
        assertEquals(Course.class, response.getCourse().getClass());
        assertEquals(Teacher.class, response.getTeacher().getClass());
        assertEquals(Subject.class, response.getSubject().getClass());
        assertEquals(1, response.getStudents().size());
        assertEquals(STATUS, response.getStatus());
    }

    @Test
    void whenUpdateThenThrowIllegalArgumentException() {
        when(repository.findById(anyLong())).thenReturn(optionalSchoolClass);
        when(repository.save(any())).thenThrow(
                new IllegalArgumentException("Status incorreto. Utilizar ACTIVE ou FINISHED para operações em turmas."));

        try {
            service.update(schoolClassDTO);
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals("Status incorreto. Utilizar ACTIVE ou FINISHED para operações em turmas.", ex.getMessage());
        }
    }

    @Test
    void WhenEnrollStudentThenReturnSuccess() {
        when(repository.save(any())).thenReturn(schoolClass);
        when(repository.findById(anyLong())).thenReturn(optionalSchoolClass);
        when(subjectService.findById(anyLong())).thenReturn(subject);
        when(studentService.findById(anyLong())).thenReturn(student);

        schoolClass.setStudents(new HashSet<>());
        SchoolClass response = service.enrollStudent(1L, 2L, 3L);

        assertNotNull(response);
        assertEquals(SchoolClass.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATE, response.getBeginDate());
        assertEquals(VACANCY - 1, response.getVacancy());
        assertEquals(Course.class, response.getCourse().getClass());
        assertEquals(Teacher.class, response.getTeacher().getClass());
        assertEquals(Subject.class, response.getSubject().getClass());
        assertEquals(HashSet.class, response.getStudents().getClass());
        assertEquals(STATUS, response.getStatus());
    }

    @Test
    void whenEnrollStudentThenThrowSchoolClassNoVacancyException() {
        when(repository.findById(anyLong())).thenReturn(optionalSchoolClass);

        try {
            optionalSchoolClass.get().setVacancy(0);
            service.enrollStudent(1L, 2L, 3L);
        } catch (Exception ex) {
            assertEquals(SchoolClassNoVacancyException.class, ex.getClass());
            assertEquals("Não há vagas disponíveis na turma de número " + schoolClass.getId() +
                    " do curso de " + schoolClass.getCourse().getName(), ex.getMessage());
        }
    }

    @Test
    void whenEnrollStudentThenThrowStudentAlreadyEnrolled() {
        when(studentService.findById(anyLong())).thenReturn(student);
        when(repository.findById(anyLong())).thenReturn(optionalSchoolClass);

        try {
            service.enrollStudent(1L, 2L, 3L);
        } catch (Exception ex) {
            assertEquals(StudentAlreadyEnrolled.class, ex.getClass());
            assertEquals("Estudante já matriculado na disciplina.", ex.getMessage());
        }
    }

    @Test
    void whenEnrollThenThrowFinishedSchoolClassException() {
        when(repository.findById(anyLong())).thenReturn(optionalSchoolClass);

        try {
            service.finishClass(anyLong());
            service.enrollStudent(1L, 2L, 3L);
        } catch (Exception ex) {
            assertEquals(FinishedSchoolClassException.class, ex.getClass());
            assertEquals("Não foi possível concluir a matrícula. Turma encerrada.", ex.getMessage());
        }
    }

    @Test
    void whenGetPerDateThenReturnAnListOfSchoolClass() {
        when(repository.findClassByBeginDate(any())).thenReturn(schoolClasses);

        List<SchoolClass> response = service.getPerDate(DATE.toString());

        assertNotNull(response);
        assertEquals(SchoolClass.class, response.get(0).getClass());
    }

    public void start() {
        course = new Course(ID, COURSE_NAME, WORKLOAD, COURSE_VALUE, MINIMUM_GRADE, MAXIMUM_GRADE);
        teacher = new Teacher(ID, NAME, CPF);
        subject = new Subject(ID, SUBJECT_NAME);
        student = new Student(ID, NAME, CPF, DATE, EMAIL, course, STATUS);
        students = Set.of(student);
        schoolClass = new SchoolClass(ID, DATE, VACANCY, course, teacher, subject, students, STATUS);
        schoolClasses = List.of(schoolClass);
        optionalSchoolClass = Optional.of(schoolClass);
        schoolClassDTO = new SchoolClassDTO(ID, DATE, VACANCY, ID, TEACHER_ID, SUBJECT_ID, STATUS);
    }
}