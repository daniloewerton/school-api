
package com.daniloewerton.com.school.services;

import com.daniloewerton.com.school.model.Subject;
import com.daniloewerton.com.school.model.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

    Subject insert(SubjectDTO subjectDTO);

    Subject findById(Long id);

    Subject update(SubjectDTO subjectDTO);

    List<Subject> listSubjectByCourse(Long idCourse);
}