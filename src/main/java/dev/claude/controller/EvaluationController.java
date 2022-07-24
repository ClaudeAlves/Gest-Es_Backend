package dev.claude.controller;

import dev.claude.api.ApiUtil;
import dev.claude.api.EvaluationApi;
import dev.claude.domain.evalutation.Mark;
import dev.claude.domain.evalutation.Test;
import dev.claude.domain.organisation.Module;
import dev.claude.domain.user.AppUser;
import dev.claude.dto.*;
import dev.claude.mapper.calendar.PeriodMapper;
import dev.claude.mapper.evaluation.MarkMapper;
import dev.claude.mapper.evaluation.TestMapper;
import dev.claude.mapper.organisation.ModuleMapper;
import dev.claude.repository.evaluation.MarkRepository;
import dev.claude.service.EvaluationService;
import dev.claude.service.UserService;
import dev.claude.service.exception.IncompleteBodyException;
import dev.claude.service.exception.InternalErrorException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Autowired
    ModuleMapper moduleMapper;
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
            throw new InternalErrorException(e.getMessage());
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
            throw new InternalErrorException(e.getMessage());
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
            throw new InternalErrorException(e.getMessage());
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
            throw new InternalErrorException(e.getMessage());
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
            throw new InternalErrorException(e.getMessage());
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
            throw new InternalErrorException(e.getMessage());
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
            throw new InternalErrorException(e.getMessage());
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
        List<TestInfoDTO> testInfoDTOS;
        try {
            testInfoDTOS = evaluationService.getTestsInfoUsers();
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());

        }
        return ResponseEntity.ok(testInfoDTOS);
    }
    /**
     * GET /evaluation/module/{idModule}/classes/{idClass}
     * Get the marks and infos for each tests in a module.
     *
     * @param idModule  (required)
     * @param idClass  (required)
     * @return Get infos successfully. (status code 200)
     */
    @Override
    public ResponseEntity<ModuleMarkInfos> getModuleInfosByClass(Long idModule, Long idClass) {
        ModuleMarkInfos moduleInfos;
        try {
            moduleInfos = evaluationService.getModuleInfosByClass(idModule, idClass);
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(moduleInfos);
    }
    /**
     * GET /evaluation/modules/classes/{idClass}
     * Get the modules names and id for selection.
     *
     * @param idClass  (required)
     * @return Get modules successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<SimpleModuleDTO>> getModulesByClass(Long idClass) {
        List<SimpleModuleDTO> moduleDTOS = new LinkedList<>();
        try {
            for (Module module : evaluationService.getModulesByClass(idClass)) {
                moduleDTOS.add(moduleMapper.toSimpleDto(module));
            }
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(moduleDTOS);
    }
    /**
     * GET /evaluation/grades/classes/{idClass}
     * Get the grades and infos for each module for a class.
     *
     * @param idClass  (required)
     * @return Get infos successfully. (status code 200)
     */
    @Override
    public ResponseEntity<GradeSynthesis> getSynthesisFromClass(Long idClass) {
        GradeSynthesis gradeSynthesis;
        try {
            gradeSynthesis = evaluationService.getSynthesisByClass(idClass);
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(gradeSynthesis);
    }
    /**
     * GET /evaluation/grades
     * Get the grades and infos for each module for the student using this endpoint.
     *
     * @return Get infos successfully. (status code 200)
     */
    @Override
    public ResponseEntity<GradeSynthesis> getSelfSynthesis() {
        GradeSynthesis gradeSynthesis;
        try {
            gradeSynthesis = evaluationService.getSelfSynthesis();
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(gradeSynthesis);
    }

    /**
     * GET /evaluation/module/{idModule}
     * Get the marks and infos for each tests in a module for the student using this endpoint.
     *
     * @param idModule  (required)
     * @return Get infos successfully. (status code 200)
     */
    @Override
    public ResponseEntity<ModuleMarkInfos> getSelfModuleInfos(Long idModule) {
        ModuleMarkInfos moduleInfos;
        try {
            moduleInfos = evaluationService.getSelfModuleInfos(idModule);
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(moduleInfos);
    }
    /**
     * GET /evaluation/modules
     * Get the modules names and id of the current student for selection.
     *
     * @return Get modules successfully. (status code 200)
     */
    public ResponseEntity<List<SimpleModuleDTO>> getSelfModules() {
        List<SimpleModuleDTO> moduleDTOS = new LinkedList<>();
        try {
            for (Module module : evaluationService.getSelfModules()) {
                moduleDTOS.add(moduleMapper.toSimpleDto(module));
            }
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(moduleDTOS);

    }
}
