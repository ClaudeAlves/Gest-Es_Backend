package dev.claude.controller;

import dev.claude.api.OrganisationApi;
import dev.claude.dto.ApiMessageDTO;
import dev.claude.service.OrganisationService;
import dev.claude.service.exception.IncompleteBodyException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "organisation")
@RestController
public class OrganisationController implements OrganisationApi {
    @Autowired
    private OrganisationService organisationService;

    /**
     * PUT /organisation/class/{idClass}/student/{idStudent} : Subs student to class.
     * Subs a student to a class.
     *
     * @param idClass  (required)
     * @param idStudent  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> classToStudent(Long idClass, Long idStudent) {
        try {
            organisationService.linkClassAndStudent(idClass, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }

    /**
     * PUT /organisation/course/{idCourse}/class/{idClass} : Subs class to course.
     * Subs a class to a course.
     *
     * @param idCourse  (required)
     * @param idClass  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> courseToClass(Long idCourse, Long idClass) {
        try {
            organisationService.linkCourseAndClass(idCourse, idClass);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }

    /**
     * PUT /organisation/module/{idModule}/student/{idStudent} : Subs student to module.
     * Subs a student to a module.
     *
     * @param idModule  (required)
     * @param idStudent  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> moduleToStudent(Long idModule, Long idStudent) {
        try {
            organisationService.linkModuleAndStudent(idModule, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }

    /**
     * PUT /organisation/subject/{idSubject}/course/{idCourse} : Adds subject to course.
     * Adds a subject to a course.
     *
     * @param idSubject  (required)
     * @param idCourse  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToCourse(Long idSubject, Long idCourse) {
        try {
            organisationService.linkSubjectAndCourse(idSubject, idCourse);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }

    /**
     * PUT /organisation/module/{idModule}/subject/{idSubject} : Adds subject to module.
     * Adds a subject to a module.
     *
     * @param idModule  (required)
     * @param idSubject  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToModule(Long idModule, Long idSubject) {
        try {
            organisationService.linkSubjectAndModule(idSubject, idModule);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }

    /**
     * PUT /organisation/subject/{idSubject}/student/{idStudent} : Subs student to subject.
     * Subs a student to a subject.
     *
     * @param idSubject  (required)
     * @param idStudent  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToStudent(Long idSubject, Long idStudent) {
        try {
            organisationService.linkSubjectAndStudent(idSubject, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }

    /**
     * PUT /organisation/course/{idCourse}/teacher/{idTeacher} : Adds teacher to course.
     * Adds a teacher to a course.
     *
     * @param idCourse  (required)
     * @param idTeacher  (required)
     * @return OK. (status code 200)
     *         or unauthorized (status code 401)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> teacherToCourse(Long idCourse, Long idTeacher) {
        try {
            organisationService.linkTeacherAndCourse(idCourse, idTeacher);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
}
