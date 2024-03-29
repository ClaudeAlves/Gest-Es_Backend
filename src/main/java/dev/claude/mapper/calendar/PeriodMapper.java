package dev.claude.mapper.calendar;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.evalutation.Test;
import dev.claude.dto.PeriodDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;


import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class PeriodMapper extends Mapper<Period, PeriodDTO, PeriodDTO> {
    @Override
    public Period toModel(PeriodDTO dtoObject) {
        return Period.builder()
                .start(dtoObject.getStart().toLocalDateTime())
                .end(dtoObject.getEnd().toLocalDateTime())
                .build();
    }
    @Override
    public PeriodDTO toDto(Period modelObject) {
        ZoneId zoneId = ZoneId.systemDefault();
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.end(ZonedDateTime.of(modelObject.getEnd(), zoneId).toOffsetDateTime());
        periodDTO.start(ZonedDateTime.of(modelObject.getStart(), zoneId).toOffsetDateTime());
        periodDTO.id(modelObject.getIdPeriod());
        periodDTO.tag(modelObject.getTag());
        periodDTO.text(modelObject.getText());

        return periodDTO;
    }
    @Override
    public Period toModelFromCreation(PeriodDTO creationObject) {
        return null;
    }

    public PeriodDTO toDtoFromTest(Test test) {
        ZoneId zoneId = ZoneId.systemDefault();
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.end(ZonedDateTime.of(test.getPeriod().getEnd(), zoneId).toOffsetDateTime());
        periodDTO.start(ZonedDateTime.of(test.getPeriod().getStart(), zoneId).toOffsetDateTime());
        periodDTO.id(test.getPeriod().getIdPeriod());
        periodDTO.tag(test.getPeriod().getTag());
        periodDTO.text(test.getText());

        return periodDTO;
    }
}
