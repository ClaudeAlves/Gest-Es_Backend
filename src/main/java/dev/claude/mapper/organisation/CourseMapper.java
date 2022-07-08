package dev.claude.mapper.organisation;

import dev.claude.domain.organisation.Course;
import dev.claude.dto.CourseDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper extends Mapper<Course, CourseDTO, CourseDTO> {
    @Override
    public Course toModel(CourseDTO dtoObject) {
        return Course.builder()
                .name(dtoObject.getName())
                .build();
    }

    @Override
    public CourseDTO toDto(Course modelObject) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(modelObject.getName());
        return courseDTO;
    }

    @Override
    public Course toModelFromCreation(CourseDTO creationObject) {
        return null;
    }
}
