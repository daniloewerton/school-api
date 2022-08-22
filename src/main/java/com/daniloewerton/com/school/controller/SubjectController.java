package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.dto.SubjectDTO;
import com.daniloewerton.com.school.services.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<SubjectDTO> insert(SubjectDTO subjectDTO) {
        return ResponseEntity.ok().body(mapper.map(service.insert(subjectDTO), SubjectDTO.class));
    }

    @PutMapping
    public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO subjectDTO) {
        return ResponseEntity.ok().body(mapper.map(service.update(subjectDTO), SubjectDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getSubjectByCourse(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok().body(service.listSubjectByCourse(id)
                .stream().map(x -> mapper.map(x, SubjectDTO.class))
                .collect(Collectors.toList()));
    }
}