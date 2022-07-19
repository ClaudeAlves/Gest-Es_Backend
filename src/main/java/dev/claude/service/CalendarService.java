package dev.claude.service;

import dev.claude.domain.calendar.Holiday;
import dev.claude.domain.calendar.Period;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.repository.calendar.HolidayRepository;
import dev.claude.repository.calendar.PeriodRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.service.exception.EntityDoesNotExistException;
import dev.claude.service.exception.InternalErrorException;
import dev.claude.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {
    @Autowired
    PeriodRepository periodRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StudentGroupRepository studentGroupRepository;
    @Autowired
    HolidayRepository holidayRepository;
    @Autowired
    CourseRepository courseRepository;
    private static final Logger logger = LoggerFactory.getLogger(CalendarService.class);

    public List<Period> getSelfPeriods() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            return getPeriodsFromUserId(optUser.get().getIdUser(), optUser);
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
    }
    public List<Period> getPeriods(Long userId) {
        Optional<AppUser> optUser = userRepository.findById(userId);
        if(optUser.isPresent()) {
            return getPeriodsFromUserId(userId, optUser);
        } else {
            throw new EntityDoesNotExistException("User doesn't exist in DB");
        }
    }
    public List<Period> getStudentGroupPeriods(Long studentGroupId) {
        Optional<StudentGroup> optGroup = studentGroupRepository.findById(studentGroupId);
        if(optGroup.isPresent()) {
            return periodRepository.findAllDistinctByCourse_StudentGroups_IdStudentGroup(studentGroupId);
        } else {
            throw new EntityDoesNotExistException("Student group doesn't exist in DB");
        }
    }
    public boolean isInHolidays(LocalDate date) {
        for(Holiday holiday : holidayRepository.findAll()) {
            if((date.isAfter(holiday.getStart()) && date.isBefore(holiday.getEnd()))
            || date.equals(holiday.getEnd()) || date.equals(holiday.getStart())) {
                return true;
            }
        }
        return false;
    }

    private List<Period> getPeriodsFromUserId(Long idUser, Optional<AppUser> optUser) {
        List<Period> periods;
        if(optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
            // user is a student
            periods =  periodRepository.findAllDistinctByCourse_StudentGroups_Students_IdUser(idUser);
        } else if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal() + 1))) {
            // user is a teacher
            periods = periodRepository.findAllDistinctByCourse_Teacher_IdUser(idUser);
        } else {
            throw new UnauthorizedException("Admin only users can't have a calendar");
        }
        try {
            return periods;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
    }
}
