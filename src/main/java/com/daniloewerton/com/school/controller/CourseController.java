package com.daniloewerton.com.school.controller;

import com.daniloewerton.com.school.services.CourseService;
import com.daniloewerton.com.school.model.dto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {

    public static final String ID = "/{id}";
    @Autowired
    private CourseService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<CourseDTO> save(@RequestBody CourseDTO objCourse) {
        return ResponseEntity.ok().body(mapper.map(service.save(objCourse), CourseDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll().stream().map(x -> mapper.map(x, CourseDTO.class)).collect(Collectors.toList()));
    }

    @PutMapping(value = ID)
    public ResponseEntity<CourseDTO> update(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        courseDTO.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(courseDTO), CourseDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<CourseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<CourseDTO> deleteSchoolClass(@RequestParam(value = "idClass") Long idSchoolClass,@RequestParam(value = "idCourse") Long idCourse) {
        service.deleteSchoolClass(idSchoolClass, idCourse);
        return ResponseEntity.noContent().build();
    }
}
