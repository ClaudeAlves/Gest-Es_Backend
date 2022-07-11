package dev.claude.controller;

import dev.claude.api.CalendarApi;
import dev.claude.domain.calendar.Period;
import dev.claude.dto.CalendarDTO;
import dev.claude.mapper.calendar.PeriodMapper;
import dev.claude.service.CalendarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Calendar")
@RestController
public class CalendarController implements CalendarApi {
    @Autowired
    CalendarService calendarService;
    @Autowired
    PeriodMapper periodMapper;
    /**
     * GET /calendar/user/{userId} : Get a specific user calendar.
     * This endpoint is used to get the calendar of a user
     *
     * @param userId  (required)
     * @return Get user calendar successfull. (status code 200)
     */
    public ResponseEntity<CalendarDTO> getCalendar(String userId) {
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
    public ResponseEntity<CalendarDTO> getClassCalendar(String classId) {
        CalendarDTO calendarDTO = new CalendarDTO();
        for(Period period : calendarService.getStudentGroupPeriods(Long.getLong(classId))) {
            calendarDTO.addPeriodsItem(periodMapper.toDto(period));
        }
        return ResponseEntity.ok(calendarDTO);
    }
}
