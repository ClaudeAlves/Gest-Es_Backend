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
    public ResponseEntity<ApiMessageDTO> classToStudent(String idClass, String idStudent) {
        try {
            organisationService.linkClassAndStudent(idClass, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> courseToClass(String idCourse, String idClass) {
        try {
            organisationService.linkCourseAndClass(idCourse, idClass);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> moduleToStudent(String idModule, String idStudent) {
        try {
            organisationService.linkModuleAndStudent(idModule, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToCourse(String idSubject, String idCourse) {
        try {
            organisationService.linkSubjectAndCourse(idSubject, idCourse);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToModule(String idModule, String idSubject) {
        try {
            organisationService.linkSubjectAndModule(idSubject, idModule);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> subjectToStudent(String idSubject, String idStudent) {
        try {
            organisationService.linkSubjectAndStudent(idSubject, idStudent);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> teacherToCourse(String idCourse, String idTeacher) {
        try {
            organisationService.linkTeacherAndCourse(idCourse, idTeacher);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.ok("Addition successful"));
    }
}
