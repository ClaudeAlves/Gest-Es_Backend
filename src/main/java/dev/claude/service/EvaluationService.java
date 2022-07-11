package dev.claude.service;

import dev.claude.domain.evalutation.Mark;
import dev.claude.domain.evalutation.Test;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.user.AppUser;
import dev.claude.dto.GradeDTO;
import dev.claude.dto.TestDTO;
import dev.claude.mapper.evaluation.TestMapper;
import dev.claude.repository.evaluation.MarkRepository;
import dev.claude.repository.evaluation.TestRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.service.exception.EntityDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createTest(Test test, Long idCourse) {
        Optional<Course> optCourse = courseRepository.findById(idCourse);
        if(optCourse.isPresent()) {
            test.setCourse(optCourse.get());
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
            for(Mark mark : markRepository.findAllByTest_Course_IdCourse(course.getIdCourse())) {
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
            for(Test test : testRepository.findAllByCourse_StudentGroups_Students_IdUser(idStudent)) {
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
}
