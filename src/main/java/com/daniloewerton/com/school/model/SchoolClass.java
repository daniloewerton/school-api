package com.daniloewerton.com.school.model;

import com.daniloewerton.com.school.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_class")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate beginDate;

    @Column(nullable = false)
    private Integer vacancy;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;

    @ManyToMany
    private Set<Student> students;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
