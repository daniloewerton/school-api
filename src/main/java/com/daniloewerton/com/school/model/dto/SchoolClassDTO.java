package com.daniloewerton.com.school.model.dto;

import com.daniloewerton.com.school.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDTO {

    private Long id;

    private LocalDate beginDate;

    private Integer vacancy;

    private Long courseId;

    private Long teacherId;

    private Long subjectId;

    private Status status;
}
