package com.daniloewerton.com.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer workload;

	@Column(nullable = false)
	private Double courseValue;

	@Column(nullable = false)
	private Double minimumGrade;

	@Column(nullable = false)
	private Double maximumGrade;
}