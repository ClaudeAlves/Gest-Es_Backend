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

    /**
     * GET /calendar/user/{userId} : Get a specific user calendar.
     * This endpoint is used to get the calendar of a user
     *
     * @param userId  (required)
     * @return Get user calendar successfull. (status code 200)
     */
    @Override
    public ResponseEntity<CalendarDTO> getCalendar(Long userId) {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getPeriods(userId)) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }

    /**
     * GET /calendar : Get a specific user calendar.
     * This endpoint is used to get the calendar of the authenticated user
     *
     * @return Get users calendar successfully. (status code 200)
     */
    @Override
    public ResponseEntity<CalendarDTO> getCalendarUser() {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getSelfPeriods()) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }

    /**
     * GET /calendar/class/{classId} : Get a class calendar.
     * This endpoint is used to get the calendar of a class
     *
     * @param classId  (required)
     * @return Get class calendar successfull. (status code 200)
     */
    @Override
    public ResponseEntity<CalendarDTO> getClassCalendar(Long classId) {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getStudentGroupPeriods(classId)) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }
}
