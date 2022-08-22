package com.daniloewerton.com.school.model.dto;

import com.daniloewerton.com.school.enums.Status;
import com.daniloewerton.com.school.model.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollDTO {

    private Long id;

    private String name;

    private String cpf;

    private LocalDate birthdate;

    private String email;

    private String courseName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course enrolledCourse;

    private Status enrollmentStatus;
}
