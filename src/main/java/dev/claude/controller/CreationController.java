package dev.claude.controller;

import dev.claude.api.CreationApi;
import dev.claude.domain.calendar.Holiday;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.Module;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.organisation.Subject;
import dev.claude.dto.*;
import dev.claude.mapper.calendar.HolidayMapper;
import dev.claude.mapper.organisation.*;
import dev.claude.service.CreationService;
import dev.claude.service.exception.IncompleteBodyException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "Creation")
@RestController
public class CreationController implements CreationApi {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private HolidayMapper holidayMapper;
    @Autowired
    private StudentGroupMapper studentGroupMapper;
    @Autowired
    private CreationService creationService;
    @Override
    public ResponseEntity<ApiMessageDTO> createCourse(CourseDTO courseDTO) {
        try {
            Course course = courseMapper.toModel(courseDTO);
            creationService.createNewCourse(course);
            creationService.createPeriodsForCourse(course);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> createModule(ModuleDTO moduleDTO) {
        try {
            Module module = moduleMapper.toModel(moduleDTO);
            creationService.createNewModule(module);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> createSubject(SubjectDTO subjectDTO) {
        try {
            Subject subject = subjectMapper.toModel(subjectDTO);
            creationService.createNewSubject(subject);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> createHoliday(HolidayDTO holidayDTO){
        try {
            Holiday holiday = holidayMapper.toModel(holidayDTO);
            creationService.createNewHoliday(holiday);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));
    }
    @Override
    public ResponseEntity<ApiMessageDTO> createClass(ClassDTO classDTO) {
        try {
            StudentGroup studentGroup = studentGroupMapper.toModel(classDTO);
            creationService.createNewStudentGroup(studentGroup);
        } catch (Exception e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Creation successful"));
    }
}