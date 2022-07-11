package dev.claude.controller;

import dev.claude.api.EvaluationApi;
import dev.claude.domain.evalutation.Mark;
import dev.claude.dto.ApiMessageDTO;
import dev.claude.dto.GradeDTO;
import dev.claude.dto.MarkDTO;
import dev.claude.dto.TestDTO;
import dev.claude.mapper.evaluation.MarkMapper;
import dev.claude.mapper.evaluation.TestMapper;
import dev.claude.service.EvaluationService;
import dev.claude.service.exception.IncompleteBodyException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Api(tags = "Evaluation")
@RestController
public class EvaluationController implements EvaluationApi {
    @Autowired
    EvaluationService evaluationService;
    @Autowired
    TestMapper testMapper;
    @Autowired
    MarkMapper markMapper;

    /**
     * POST /evluation/test : create test
     * This endpoint is used to create a test
     *
     * @param testDTO  (optional)
     * @return Creation successful. (status code 201)
     */
    public ResponseEntity<ApiMessageDTO> createTest(TestDTO testDTO) {
        try {
            evaluationService.createTest(testMapper.toModel(testDTO), testDTO.getCourseId());
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
    public ResponseEntity<ApiMessageDTO> noteStudent(MarkDTO markDTO) {
        try {
            Mark mark = markMapper.toModel(markDTO);
            evaluationService.noteStudent(mark, markDTO.getStudentId(), markDTO.getTestId());
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));

    }
}
