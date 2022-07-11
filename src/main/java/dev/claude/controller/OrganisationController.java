package dev.claude.controller;

import dev.claude.api.OrganisationApi;
import dev.claude.dto.ApiMessageDTO;
import dev.claude.service.OrganisationService;
import dev.claude.service.exception.IncompleteBodyException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Organisation")
@RestController
public class OrganisationController implements OrganisationApi {
    @Autowired
    private OrganisationService organisationService;
    @Override
    public ResponseEntity<ApiMessageDTO> classToStudent(Long idClass, Long idStudent) {
        try {
            organisationService.linkClassAndStudent(idClass, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> courseToClass(Long idCourse, Long idClass) {
        try {
            organisationService.linkCourseAndClass(idCourse, idClass);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> moduleToStudent(Long idModule, Long idStudent) {
        try {
            organisationService.linkModuleAndStudent(idModule, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToCourse(Long idSubject, Long idCourse) {
        try {
            organisationService.linkSubjectAndCourse(idSubject, idCourse);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToModule(Long idModule, Long idSubject) {
        try {
            organisationService.linkSubjectAndModule(idSubject, idModule);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToStudent(Long idSubject, Long idStudent) {
        try {
            organisationService.linkSubjectAndStudent(idSubject, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
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
