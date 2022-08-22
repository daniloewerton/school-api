package com.daniloewerton.com.school.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Long id;

    private String name;

    private Integer workload;

    private Double courseValue;

    private Double minimumGrade;

    private Double maximumGrade;


}
