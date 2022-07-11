package dev.claude.mapper.calendar;

import dev.claude.domain.calendar.Holiday;
import dev.claude.dto.HolidayDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.OffsetTime;

@Component
public class HolidayMapper extends Mapper<Holiday, HolidayDTO, HolidayDTO> {
    @Override
    public Holiday toModel(HolidayDTO dtoObject) {
        return Holiday.builder()
                .start(dtoObject.getStart().toLocalDate())
                .end(dtoObject.getEnd().toLocalDate())
                .build();
    }
    @Override
    public HolidayDTO toDto(Holiday modelObject) {
        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setStart(modelObject.getStart().atTime(OffsetTime.now()));
        holidayDTO.setEnd(modelObject.getEnd().atTime(OffsetTime.now()));
        return holidayDTO;
    }

    @Override
    public Holiday toModelFromCreation(HolidayDTO creationObject) {
        return null;
    }

}
