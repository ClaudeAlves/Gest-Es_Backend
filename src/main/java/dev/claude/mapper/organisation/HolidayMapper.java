package dev.claude.mapper.organisation;

import dev.claude.domain.calendar.Holiday;
import dev.claude.domain.organisation.Course;
import dev.claude.dto.CourseDTO;
import dev.claude.dto.HolidayDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.OffsetTime;

@Component
public class HolidayMapper extends Mapper<Holiday, HolidayDTO, HolidayDTO> {
    @Override
    public Holiday toModel(HolidayDTO dtoObject) {
        return Holiday.builder()
                .start(Date.valueOf(dtoObject.getStart().toLocalDate()))
                .end(Date.valueOf(dtoObject.getEnd().toLocalDate()))
                .build();
    }
    @Override
    public HolidayDTO toDto(Holiday modelObject) {
        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setStart(modelObject.getStart().toLocalDate().atTime(OffsetTime.now()));
        holidayDTO.setEnd(modelObject.getEnd().toLocalDate().atTime(OffsetTime.now()));
        return holidayDTO;
    }

    @Override
    public Holiday toModelFromCreation(HolidayDTO creationObject) {
        return null;
    }

}
