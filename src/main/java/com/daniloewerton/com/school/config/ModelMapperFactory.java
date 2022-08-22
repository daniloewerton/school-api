package com.daniloewerton.com.school.config;

import com.daniloewerton.com.school.model.SchoolClass;
import com.daniloewerton.com.school.model.Student;
import com.daniloewerton.com.school.model.dto.SchoolClassInsertDTO;
import com.daniloewerton.com.school.model.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperFactory {

    @Bean
    public ModelMapper mapperFactory() {
        var modelMapper =  new ModelMapper();

        modelMapper.createTypeMap(Student.class, StudentDTO.class)
                .addMapping(src -> src.getEnrolledCourse().getName(), StudentDTO::setCourseName);

        modelMapper.createTypeMap(SchoolClass.class, SchoolClassInsertDTO.class)
                .addMapping(src -> src.getCourse().getName(), SchoolClassInsertDTO::setCourseName);

        return modelMapper;
    }
}
