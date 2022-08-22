package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.dto.StudentDTO;
import com.daniloewerton.com.school.model.dto.StudentEnrollDTO;
import com.daniloewerton.com.school.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    public static final String ID = "/{id}";
    @Autowired
    private StudentService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<StudentDTO> enroll(@RequestBody StudentEnrollDTO studentDTO) {
        return ResponseEntity.ok().body(mapper.map(service.enroll(studentDTO), StudentDTO.class));
    }

    @PutMapping
    public ResponseEntity<StudentDTO> update(@RequestBody StudentEnrollDTO studentEnrollDTO) {
        return ResponseEntity.ok().body(mapper.map(service.update(studentEnrollDTO), StudentDTO.class));
    }

    @GetMapping(ID)
    public ResponseEntity<List<StudentDTO>> findStudentByCourse(@PathVariable Long id) {
        return ResponseEntity.ok(service.findStudentByCourse(id).stream().map(x -> mapper.map(x, StudentDTO.class)).collect(Collectors.toList()));
    }

    @DeleteMapping(ID)
    public ResponseEntity<StudentDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}