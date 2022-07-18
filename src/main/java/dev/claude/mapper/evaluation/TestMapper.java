package dev.claude.mapper.evaluation;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.evalutation.Test;
import dev.claude.dto.TestDTO;
import dev.claude.mapper.Mapper;
import dev.claude.repository.calendar.PeriodRepository;
import dev.claude.repository.organisation.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TestMapper extends Mapper<Test, TestDTO, TestDTO> {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    PeriodRepository periodRepository;
    @Override
    public Test toModel(TestDTO dtoObject) {
        return Test.builder()
                .text(dtoObject.getText())
                .build();
    }

    @Override
    public TestDTO toDto(Test modelObject) {
        ZoneId zoneId = ZoneId.systemDefault();
        TestDTO testDTO = new TestDTO();
        testDTO.setEnd(ZonedDateTime.of(modelObject.getPeriod().getEnd(), zoneId).toOffsetDateTime());
        testDTO.setStart(ZonedDateTime.of(modelObject.getPeriod().getStart(), zoneId).toOffsetDateTime());
        testDTO.setText(modelObject.getText());
        return testDTO;
    }

    @Override
    public Test toModelFromCreation(TestDTO creationObject) {
        return null;
    }
}
