package dev.claude.service;

import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.Module;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.ModuleRepository;
import dev.claude.repository.organisation.SubjectRepository;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.service.exception.EntityDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public void linkClassAndStudent(Long idClass, Long idStudent) {
        if(studentGroupRepository.existsById(idClass)) {
            // class exists
            if (userRepository.existsById(idStudent)) {
                // user exists
                AppUser user = userRepository.getById(idStudent);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal()))) {
                    // user is a student
                    StudentGroup studentGroup = studentGroupRepository.getById(idClass);
                    user.getStudentGroups().add(studentGroup);
                    studentGroup.getStudents().add(user);
                    userRepository.save(user);
                    studentGroupRepository.save(studentGroup);
                } else {
                    throw new EntityDoesNotExistException("User isn't a student!");
                }
            } else {
                throw new EntityDoesNotExistException("User does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Class does not exist!");
        }
    }
    public void linkCourseAndClass(Long idCourse, Long idClass) {
        if(courseRepository.existsById(idCourse)) {
            // course exists
            if(studentGroupRepository.existsById(idClass)) {
                // class exists
                StudentGroup studentGroup = studentGroupRepository.getById(idClass);
                Course course = courseRepository.getById(idCourse);
                studentGroup.setCourse(course);
                studentGroupRepository.save(studentGroup);
            } else {
                throw new EntityDoesNotExistException("Class does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Course does not exist!");
        }
    }
    public void linkModuleAndStudent(Long idModule, Long idStudent) {
        if(moduleRepository.existsById(idModule)) {
            // module exists
            if (userRepository.existsById(idStudent)) {
                // user exists
                AppUser user = userRepository.getById(idStudent);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal()))) {
                    // user is a student
                    user.getModules().add(moduleRepository.getById(idModule));
                    userRepository.save(user);
                } else {
                    throw new EntityDoesNotExistException("User isn't a student!");
                }
            } else {
                throw new EntityDoesNotExistException("User does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Module does not exist!");
        }
    }
    public void linkSubjectAndCourse(Long idSubject, Long idCourse) {
        if(subjectRepository.existsById(idSubject)) {
            // subject exists
            if(courseRepository.existsById(idCourse)) {
                // course exists
                Course course = courseRepository.getById(idCourse);
                course.setSubject(subjectRepository.getById(idSubject));
                courseRepository.save(course);
            } else {
                throw new EntityDoesNotExistException("Course does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }
    public void linkSubjectAndModule(Long idSubject, Long idModule) {
        if(subjectRepository.existsById(idSubject)) {
            // subject exists
            if(moduleRepository.existsById(idModule)) {
                // module exists
                Module module = moduleRepository.getById(idModule);
                module.getSubjects().add(subjectRepository.getById(idSubject));
                moduleRepository.save(module);
            } else {
                throw new EntityDoesNotExistException("Module does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }
    public void linkSubjectAndStudent(Long idSubject, Long idStudent) {
        if(subjectRepository.existsById(idSubject)) {
            // subject exists
            if(userRepository.existsById(idStudent)) {
                // user exists
                AppUser user = userRepository.getById(idStudent);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal()))) {
                    // user is a student
                    user.getSubjects().add(subjectRepository.getById(idSubject));
                    userRepository.save(user);
                } else {
                    throw new EntityDoesNotExistException("User isn't a student!");
                }
            } else {
                throw new EntityDoesNotExistException("User does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }
    public void linkTeacherAndCourse(Long idCourse, Long idTeacher) {
        if(courseRepository.existsById(idCourse)) {
            // course exists
            if(userRepository.existsById(idTeacher)) {
                // user exists
                AppUser user = userRepository.getById(idTeacher);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal()))) {
                    // user is a teacher
                    Course course = courseRepository.getById(idCourse);
                    course.setTeacher(user);
                    courseRepository.save(course);
                } else {
                    throw new EntityDoesNotExistException("User isn't a teacher!");
                }
            } else {
                throw new EntityDoesNotExistException("User does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Course does not exist!");
        }
    }
}
