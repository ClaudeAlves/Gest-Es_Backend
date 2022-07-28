package dev.claude.controller;

import dev.claude.api.UserApi;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.user.AppUser;
import dev.claude.dto.*;
import dev.claude.mapper.organisation.CourseMapper;
import dev.claude.mapper.organisation.StudentGroupMapper;
import dev.claude.mapper.user.UserMapper;
import dev.claude.service.UserService;
import dev.claude.service.exception.InternalErrorException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Api(tags = "user")
@RestController
public class UserController implements UserApi {

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentGroupMapper studentGroupMapper;
    /**
     * DELETE /users/{username} : Delete a specific user.
     * This private endpoint is used to remove one user.
     *
     * @param username  (required)
     * @return User deletion successful. (status code 200)
     */
    @Override
    public ResponseEntity<ApiMessageDTO> deleteUser(String username) {
        try {
            userService.deleteUser(username);
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(ApiHelper.ok("Delete successful"));
    }


    /**
     * DELETE /admin/users : Delete all users.
     * This private endpoint is used to remove all users.
     *
     * @return Users deletion successful. (status code 200)
     */

    @Override
    public ResponseEntity<ApiMessageDTO> deleteUsers() {
        try {
            List<AppUser> users = userService.getUsers();
            for(AppUser user : users) {
                deleteUser(user.getUsername());
            }
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(ApiHelper.ok("Delete successful"));
    }


    /**
     * GET /user/courses : Get ids of courses.
     * This endpoint serves the teachers to get the ids of his courses.
     *
     * @return Get courses ids successful. (status code 200)
     */

    @Override
    public ResponseEntity<List<CourseDTO>> getCourses() {
        List<CourseDTO> dtoCourses = new LinkedList<>();
        try {
            List<Course> courses = userService.getCourses();
            for(Course course : courses) {
                dtoCourses.add(courseMapper.toDto(course));
            }
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(dtoCourses);
    }


    /**
     * GET /teacher/students/{idCourse} : Get students ids from course.
     * This endpoint serves the teachers to get the students ids of a specific course.
     *
     * @param idCourse  (required)
     * @return Get students id successful. (status code 200)
     */

    @Override
    public ResponseEntity<List<UserSimpleDTO>> getStudentsFromCourse(Long idCourse) {
        List<UserSimpleDTO> dtoStudents = new LinkedList<>();
        try {
            List<AppUser> students = userService.getStudentsFromCourseId(idCourse);
            for(AppUser student : students) {
                dtoStudents.add(userMapper.toUserSimpleDTO(student));
            }
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(dtoStudents);

    }


    /**
     * GET /users/{username} : Get a specific user.
     * This private endpoint is used to get one user.
     *
     * @param username  (required)
     * @return Get user successful. (status code 200)
     */

    @Override
    public ResponseEntity<UserSimpleDTO> getUser(String username) {
        UserSimpleDTO user;
        try {
            user = userMapper.toUserSimpleDTO(userService.getUser(username));
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(user);

    }


    /**
     * GET /admin/users : Get all users.
     * This private endpoint is used to get all users.
     *
     * @return Get users successful. (status code 200)
     */

    @Override
    public ResponseEntity<List<UserSimpleDTO>> getUsers() {
        List<UserSimpleDTO> usersDTO = new LinkedList<>();
        try {
            List<AppUser> users = userService.getUsers();
            for(AppUser user : users) {
                usersDTO.add(userMapper.toUserSimpleDTO(user));
            }
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(usersDTO);

    }


    /**
     * PUT /users/{username} : Update a specific user.
     * This private endpoint is used to modify one user.
     *
     * @param username  (required)
     * @param userModificationDTO  (optional)
     * @return User update successful. (status code 200)
     */

    @Override
    public ResponseEntity<UserDTO> updateUser(String username, UserModificationDTO userModificationDTO) {
        UserDTO user;
        try {
            user = userMapper.toDto(userService.updateUser(userMapper.toModelFromModification(userModificationDTO)));
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(user);

    }
    /**
     * GET /teacher/classes : Get Classes
     * This endpoint serves a teacher to get his classes
     *
     * @return Get courses successful. (status code 200)
     */
    @Override
    public ResponseEntity<List<ClassDTO>> getClasses() {
        List<ClassDTO> dtoClass = new LinkedList<>();
        try {
            List<StudentGroup> studentGroups = userService.getStudentGroups();
            for(StudentGroup studentGroup : studentGroups) {
                dtoClass.add(studentGroupMapper.toDto(studentGroup));
            }
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(dtoClass);
    }

    /**
     * GET /teacher/classes/course/{idCourse} : Get classes from course.
     * This endpoint serves to get the classes who participate in a specific course.
     *
     * @param idCourse  (required)
     * @return Get classes successful. (status code 200)
     */
    @Override
    public ResponseEntity<List<ClassDTO>> getClassesFromCourse(Long idCourse){
        List<ClassDTO> dtoClasses = new LinkedList<>();
        try {
            for(StudentGroup studentGroup : userService.getClassFromCourse(idCourse)) {
                dtoClasses.add(studentGroupMapper.toDto(studentGroup));
            }
        } catch (Error e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
        return ResponseEntity.ok(dtoClasses);
    }
}
