package com.daniloewerton.com.school.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassInsertDTO {

    private Long id;

    private LocalDate beginDate;

    private Integer vacancy;

    private String courseName;

    private String teacherName;

    @JsonIgnore
    private Long studentId;
}
