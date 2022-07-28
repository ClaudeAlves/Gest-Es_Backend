package dev.claude.service;

import dev.claude.domain.calendar.Period;
import dev.claude.utils.TimeUtils;
import dev.claude.domain.calendar.Holiday;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.repository.calendar.HolidayRepository;
import dev.claude.repository.calendar.PeriodRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.ModuleRepository;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.organisation.SubjectRepository;
import dev.claude.domain.organisation.Module;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.Subject;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.service.exception.EntityAlreadyExistException;
import dev.claude.service.exception.InternalErrorException;
import dev.claude.service.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreationService {
    @Autowired
    TimeUtils timeUtils;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    CalendarService calendarService;

    /**
     * Creates a module if user is an admin.
     * @param module module to save to DB
     */
    public void createNewModule(Module module) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // user is an admin
                if (moduleRepository.existsByName(module.getName())) {
                    throw new EntityAlreadyExistException("Module name is already taken !");
                } else if (moduleRepository.existsByAbbreviation(module.getAbbreviation())) {
                    throw new EntityAlreadyExistException("Module abbreviation is already taken !");
                } else if (moduleRepository.existsByNumber(module.getNumber())) {
                    throw new EntityAlreadyExistException("Module number is already taken !");
                } else {
                    try {
                        moduleRepository.save(module);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new InternalErrorException(e.getMessage());
                    }
                }
            } else {
                throw new UnauthorizedException("Can't create subject if not admin");
            }
        }
    }

    /**
     * Creates a subject if user is an admin.
     * @param subject subject to save to DB
     */
    public void createNewSubject(Subject subject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if(optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // user is an admin
                if(subjectRepository.existsByName(subject.getName())) {
                    throw new EntityAlreadyExistException("Subject name is already taken !");
                } else if(subjectRepository.existsByAbbreviation(subject.getAbbreviation())) {
                    throw new EntityAlreadyExistException("Subject abbreviation is already taken !");
                } try {
                    subjectRepository.save(subject);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new InternalErrorException(e.getMessage());
                }
            } else {
                throw new UnauthorizedException("Can't create subject if not admin");
            }
        }
    }

    /**
     * Creates a course if user is an admin.
     * @param course course to save in DB
     */
    public void createNewCourse(Course course) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // user is an admin
                if (courseRepository.existsByName(course.getName())) {
                    throw new EntityAlreadyExistException("Course name is already taken !");
                }
                try {
                    courseRepository.save(course);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new InternalErrorException(e.getMessage());
                }
            } else {
                throw new UnauthorizedException("Can't create course if not admin");
            }
        }
    }

    /**
     * Creates a holiday if user is an admin.
     * @param holiday holiday to save in DB
     */
    public void createNewHoliday(Holiday holiday) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // user is an admin
                try {
                    holidayRepository.save(holiday);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new InternalErrorException(e.getMessage());
                }
            } else {
                throw new UnauthorizedException("Can't create holiday if not admin");
            }
        }
    }

    /**
     * Creates a student groups if user is an admin.
     * @param studentGroup student group to save in DB
     */
    public void createNewStudentGroup(StudentGroup studentGroup) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // user is an admin
                try {
                    studentGroupRepository.save(studentGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new InternalErrorException(e.getMessage());
                }
            } else {
                throw new UnauthorizedException("Can't create class if not admin");
            }
        }
    }

    /**
     * Creates periods for a course.
     * @param course course to create periods for
     */
    public void createPeriodsForCourse(Course course) {
        for(Integer periodOfTheWeek : course.getPeriodsOfTheWeek()) { // for every period of the week from a course
            for (LocalDate date = course.getStart(); !course.getStart().isAfter(course.getEnd()) && !date.isAfter(course.getEnd()); date = date.plusDays(1)) { // iterate over every date during the course
                if (timeUtils.getDayOfTheWeekFromPeriodOfTheWeek(periodOfTheWeek) == date.getDayOfWeek().getValue() // if the day of the week matches the day from the period
                        && !calendarService.isInHolidays(date)) { // and this day isn't in the holidays
                    LocalDateTime start = timeUtils.getStartFromPeriod(periodOfTheWeek, date); //then we build the period at the right time of the day
                    LocalDateTime end = timeUtils.getEndFromPeriod(periodOfTheWeek, date);
                    Period period = Period.builder()
                            .start(start)
                            .end(end)
                            .course(course)
                            .build();
                    periodRepository.save(period);
                }

            }
        }
    }

}
