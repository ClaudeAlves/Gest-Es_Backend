package dev.claude.mapper.organisation;

import dev.claude.domain.organisation.Course;
import dev.claude.dto.CourseDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CourseMapper extends Mapper<Course, CourseDTO, CourseDTO> {
    @Override
    public Course toModel(CourseDTO dtoObject) {
        return Course.builder()
                .name(dtoObject.getName())
                .start(Date.valueOf(dtoObject.getStart()))
                .end(Date.valueOf(dtoObject.getEnd()))
                .periodsOfTheWeek(dtoObject.getPeriodsOfTheWeek())
                .build();
    }

    @Override
    public CourseDTO toDto(Course modelObject) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(modelObject.getName());
        courseDTO.setStart(modelObject.getStart().toLocalDate());
        courseDTO.setEnd(modelObject.getEnd().toLocalDate());
        courseDTO.setPeriodsOfTheWeek(modelObject.getPeriodsOfTheWeek().stream().toList());
        return courseDTO;
    }

    @Override
    public Course toModelFromCreation(CourseDTO creationObject) {
        return null;
    }
}
