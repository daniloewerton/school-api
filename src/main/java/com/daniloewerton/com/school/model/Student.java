package com.daniloewerton.com.school.model;

import com.daniloewerton.com.school.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course enrolledCourse;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status enrollmentStatus;
}
