package dev.claude.service;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.evalutation.Mark;
import dev.claude.domain.evalutation.Test;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.dto.GradeDTO;
import dev.claude.dto.TestDTO;
import dev.claude.mapper.evaluation.TestMapper;
import dev.claude.repository.calendar.PeriodRepository;
import dev.claude.repository.evaluation.MarkRepository;
import dev.claude.repository.evaluation.TestRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.service.exception.EntityDoesNotExistException;
import dev.claude.service.exception.InternalErrorException;
import dev.claude.service.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    MarkRepository markRepository;
    @Autowired
    TestMapper testMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StudentGroupRepository studentGroupRepository;
    @Autowired
    PeriodRepository periodRepository;

    public void createTest(Test test, TestDTO dto) {
        Optional<Course> optCourse = courseRepository.findById(dto.getCourseId());
        if(optCourse.isPresent()) {
            Period testPeriod = Period.builder()
                    .course(optCourse.get())
                    .end(dto.getEnd().toLocalDateTime())
                    .start(dto.getStart().toLocalDateTime())
                    .text(dto.getText())
                    .tag("test " + optCourse.get().getName())
                    .build();
            periodRepository.save(testPeriod);
            test.setPeriod(testPeriod);
            test.setNumber(testRepository.countAllByPeriod_Course_IdCourse(dto.getCourseId()));
            testRepository.save(test);
        } else {
            throw new EntityDoesNotExistException("Course doesn't exist");
        }
    }
    public List<GradeDTO> getGradesFromStudentId(Long idStudent) {
        List<Course> courses = courseRepository.findAllByStudentGroups_Students_IdUser(idStudent);
        List<GradeDTO> grades = new LinkedList<>();
        for(Course course : courses) {
            Double grade = 0D;
            double testNumber = 0D;
            for(Mark mark : markRepository.findAllByTest_Period_Course_IdCourse(course.getIdCourse())) {
                grade += mark.getValue();
                testNumber++;
            }
            GradeDTO gradeDTO = new GradeDTO();
            gradeDTO.setCourseName(course.getName());
            gradeDTO.setValue(grade/testNumber);
            grades.add(new GradeDTO());
        }
        return grades;
    }
    public List<TestDTO> getCourseTestsFromStudentId(Long idCourse, Long idStudent) {
        List<TestDTO> tests = new LinkedList<>();
        Optional<Course> optCourse = courseRepository.findById(idCourse);
        if (optCourse.isPresent()) {
            for(Test test : testRepository.findAllByPeriod_Course_StudentGroups_Students_IdUser(idStudent)) {
                tests.add(testMapper.toDto(test));
            }
        } else {
            throw new EntityDoesNotExistException("Course doesn't exist");
        }
        return tests;
    }
    public void noteStudent(Mark mark, Long idStudent, Long idTest) {
        Optional<AppUser> optStudent = userRepository.findById(idStudent);
        Optional<Test> optTest = testRepository.findById(idTest);
        if(optStudent.isEmpty()) {
            throw new EntityDoesNotExistException("Student doesn't exist");
        } else if (optTest.isEmpty()){
            throw new EntityDoesNotExistException("Test doesn't exist");
        } else {
            mark.setStudent(optStudent.get());
            mark.setTest(optTest.get());
            markRepository.save(mark);
        }
    }
    public List<Test> getSelfTests() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            return getTestsFromUserId(optUser.get().getIdUser(), optUser);
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
    }
    public List<Test> getTests(Long idUser) {
        Optional<AppUser> optUser = userRepository.findById(idUser);
        if(optUser.isPresent()) {
            return getTestsFromUserId(idUser, optUser);
        } else {
            throw new EntityDoesNotExistException("User doesn't exist in DB");
        }
    }

    public List<Test> getStudentGroupTests(Long idStudentGroup) {
        Optional<StudentGroup> optGroup = studentGroupRepository.findById(idStudentGroup);
        if(optGroup.isPresent()) {
            return testRepository.findAllByPeriod_Course_StudentGroups_IdStudentGroup(idStudentGroup);
        } else {
            throw new EntityDoesNotExistException("Student group doesn't exist in DB");
        }
    }

    private List<Test> getTestsFromUserId(Long idUser, Optional<AppUser> optUser) {
        List<Test> tests;
        if(optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
            // user is a student
            tests =  testRepository.findAllByPeriod_Course_StudentGroups_Students_IdUser(idUser);
        } else if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal() + 1))) {
            // user is a teacher
            tests = testRepository.findAllByPeriod_Course_Teacher_IdUser(idUser);
        } else {
            throw new UnauthorizedException("Admin only users can't have tests");
        }
        try {
            return tests;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
    }
}
