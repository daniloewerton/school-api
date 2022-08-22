package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.model.dto.SchoolClassDTO;
import com.daniloewerton.com.school.services.SchoolClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class")
public class SchoolClassController {

    public static final String ID = "/{id}";
    public static final String ENROLL = "/enroll";
    @Autowired
    private SchoolClassService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<SchoolClassDTO> insert(@RequestBody SchoolClassDTO schoolClassDTO) {
        return ResponseEntity.ok().body(mapper.map(service.insert(schoolClassDTO), SchoolClassDTO.class));
    }

    @PutMapping
    public ResponseEntity<SchoolClassDTO> update(@RequestBody SchoolClassDTO schoolClassDTO) {
        return ResponseEntity.ok().body(mapper.map(service.update(schoolClassDTO), SchoolClassDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getPerDate(@RequestParam String date) {
        return ResponseEntity.ok(service.getPerDate(date).stream().map(x -> mapper.map(x, SchoolClassDTO.class)).collect(Collectors.toList()));
    }

    @PutMapping(ENROLL)

    public ResponseEntity<SchoolClassDTO> enrollStudent(
            @RequestParam(name="idStudent") Long idStudent,
            @RequestParam(name="idSubject") Long idSubject,
            @RequestParam(name="idSchoolClass") Long idSchoolClass) {

        return ResponseEntity.ok().body(mapper.map(service.enrollStudent(idStudent, idSubject, idSchoolClass), SchoolClassDTO.class));
    }

    @DeleteMapping(ID)
    public ResponseEntity<SchoolClassDTO> finishClass(@PathVariable Long id) {
        service.finishClass(id);
        return ResponseEntity.noContent().build();
    }
}