package dev.claude.controller;

import dev.claude.api.EvaluationApi;
import dev.claude.domain.evalutation.Mark;
import dev.claude.domain.evalutation.Test;
import dev.claude.domain.user.AppUser;
import dev.claude.dto.*;
import dev.claude.mapper.calendar.PeriodMapper;
import dev.claude.mapper.evaluation.MarkMapper;
import dev.claude.mapper.evaluation.TestMapper;
import dev.claude.repository.evaluation.MarkRepository;
import dev.claude.service.EvaluationService;
import dev.claude.service.UserService;
import dev.claude.service.exception.IncompleteBodyException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Api(tags = "evaluation")
@RestController
public class EvaluationController implements EvaluationApi {
    @Autowired
    EvaluationService evaluationService;
    @Autowired
    TestMapper testMapper;
    @Autowired
    MarkMapper markMapper;
    @Autowired
    PeriodMapper periodMapper;
    @Autowired
    MarkRepository markRepository;
    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);


    /**
     * POST /evluation/test : create test
     * This endpoint is used to create a test
     *
     * @param testDTO  (optional)
     * @return Creation successful. (status code 201)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> createTest(TestDTO testDTO) {
        try {
            evaluationService.createTest(testMapper.toModel(testDTO), testDTO);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));

    }


    /**
     * GET /evaluation/grades/{idStudent}
     * Get the grades of a student.
     *
     * @param idStudent  (required)
     * @return Get student test successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<GradeDTO>> getGrades(Long idStudent) {
        List<GradeDTO> grades;
        try {
            grades = evaluationService.getGradesFromStudentId(idStudent);
        } catch (Exception e){
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(grades);
    }


    /**
     * GET /evaluation/marks/{idCourse}/student/{idStudent}
     * Get the tests of a student for a specific course.
     *
     * @param idCourse  (required)
     * @param idStudent  (required)
     * @return Get student test successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<TestDTO>> getStudentTests(Long idCourse, Long idStudent) {
        List<TestDTO> tests;
        try {
            tests = evaluationService.getCourseTestsFromStudentId(idCourse, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(tests);
    }
    /**
     * POST /evaluation/mark : Notes a student test.
     * This endpoint is used to set a mark for a student on a specific test.
     *
     * @param markDTO  (optional)
     * @return Creation successful. (status code 201)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> noteStudent(MarkDTO markDTO) {
        try {
            Mark mark = markMapper.toModel(markDTO);
            evaluationService.noteStudent(mark, markDTO.getStudentId(), markDTO.getTestId());
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));
    }
    @Override
    public ResponseEntity<List<PeriodDTO>> getTests(Long idUser) {
        List<PeriodDTO> testsDTO = new LinkedList<>();
        try {
            for(Test test : evaluationService.getTests(idUser)) {
                testsDTO.add(periodMapper.toDtoFromTest(test));
            }
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(testsDTO);
    }
    @Override
    public ResponseEntity<List<PeriodDTO>> getTestsUser() {
        List<PeriodDTO> testsDTO = new LinkedList<>();
        try {
            for(Test test : evaluationService.getSelfTests()) {
                testsDTO.add(periodMapper.toDtoFromTest(test));
            }
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(testsDTO);
    }
    @Override
    public ResponseEntity<List<PeriodDTO>> getClassTests(Long idClass) {
        List<PeriodDTO> testsDTO = new LinkedList<>();
        try {
            for(Test test : evaluationService.getStudentGroupTests(idClass)) {
                testsDTO.add(periodMapper.toDtoFromTest(test));
            }
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(testsDTO);
    }

    /**
     * GET /evalutation/tests/info
     * Get the tests informations for the user.
     *
     * @return Get tests infos successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<TestInfoDTO>> getTestsInfoUser() {
        List<TestInfoDTO> testInfoDTOS = new LinkedList<>();
        try {
            for(Test test : evaluationService.getSelfTests()) {
                TestInfoDTO testInfoDTO = new TestInfoDTO();
                List<TestInfoDTOStudents> testInfoDTOStudents = new LinkedList<>();
                for(AppUser student : userService.getStudentsFromTest(test.getIdTest())) {
                    Optional<Mark> mark = markRepository.findByTestAndAndStudent(test, student);
                    TestInfoDTOStudents studentDTO = new TestInfoDTOStudents();
                    studentDTO.setName(student.getUsername());
                    studentDTO.setId(student.getIdUser());
                    if(mark.isPresent()) {
                        studentDTO.setTestValue(mark.get().getValue());
                    } else {
                        studentDTO.setTestValue(null);
                    }
                    testInfoDTOStudents.add(studentDTO);

                }
                testInfoDTO.setStudents(testInfoDTOStudents);
                testInfoDTO.setCourseId(test.getPeriod().getCourse().getIdCourse());
                testInfoDTO.setTestName(test.getPeriod().getTag());
                testInfoDTO.setTestId(test.getIdTest());
                testInfoDTO.setText(test.getText());
                testInfoDTOS.add(testInfoDTO);
            }
        } catch (Exception e) {
            throw new IncompleteBodyException();

        }
        return ResponseEntity.ok(testInfoDTOS);
    }
}
