package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.dto.TeacherDTO;
import com.daniloewerton.com.school.services.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<TeacherDTO> enroll(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(mapper.map(service.enroll(teacherDTO), TeacherDTO.class));
    }

    @PutMapping
    public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(mapper.map(service.update(teacherDTO), TeacherDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> findAll() {
        return ResponseEntity.ok(service.findAll()
                .stream().map(x -> mapper.map(x, TeacherDTO.class))
                .collect(Collectors.toList()));
    }

    @DeleteMapping
    public ResponseEntity<TeacherDTO> delete(@RequestParam(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}