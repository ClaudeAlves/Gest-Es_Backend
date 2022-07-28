package dev.claude.mapper.calendar;

import dev.claude.domain.calendar.Holiday;
import dev.claude.dto.HolidayDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class HolidayMapper extends Mapper<Holiday, HolidayDTO, HolidayDTO> {
    @Override
    public Holiday toModel(HolidayDTO dtoObject) {
        return Holiday.builder()
                .start(dtoObject.getStart())
                .end(dtoObject.getEnd())
                .description(dtoObject.getText())
                .build();
    }
    @Override
    public HolidayDTO toDto(Holiday modelObject) {
        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setStart(modelObject.getStart());
        holidayDTO.setEnd(modelObject.getEnd());
        holidayDTO.setText(modelObject.getDescription());
        return holidayDTO;
    }

    @Override
    public Holiday toModelFromCreation(HolidayDTO creationObject) {
        return null;
    }

}
