package dev.claude.controller;

import dev.claude.api.CalendarApi;
import dev.claude.domain.calendar.Period;
import dev.claude.dto.CalendarDTO;
import dev.claude.mapper.calendar.PeriodMapper;
import dev.claude.service.CalendarService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "calendar")
@RestController
public class CalendarController implements CalendarApi {
    @Autowired
    CalendarService calendarService;
    @Autowired
    PeriodMapper periodMapper;
    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);


    @Override
    public ResponseEntity<CalendarDTO> getCalendar(Long userId) {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getPeriods(userId)) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }
    @Override
    public ResponseEntity<CalendarDTO> getCalendarUser() {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getSelfPeriods()) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }
    @Override
    public ResponseEntity<CalendarDTO> getClassCalendar(Long classId) {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getStudentGroupPeriods(classId)) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }
}
