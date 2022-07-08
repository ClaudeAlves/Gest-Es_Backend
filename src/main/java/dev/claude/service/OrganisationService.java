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

    public void linkClassAndStudent(String idClass, String idStudent) {
        if(studentGroupRepository.existsById(Long.getLong(idClass))) {
            // class exists
            if (userRepository.existsById(Long.getLong(idStudent))) {
                // user exists
                AppUser user = userRepository.getById(Long.getLong(idStudent));
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal()))) {
                    // user is a student
                    StudentGroup studentGroup = studentGroupRepository.getById(Long.getLong(idClass));
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
    public void linkCourseAndClass(String idCourse, String idClass) {
        if(courseRepository.existsById(Long.getLong(idCourse))) {
            // course exists
            if(studentGroupRepository.existsById(Long.getLong(idClass))) {
                // class exists
                StudentGroup studentGroup = studentGroupRepository.getById(Long.getLong(idClass));
                Course course = courseRepository.getById(Long.getLong(idCourse));
                studentGroup.setCourse(course);
                studentGroupRepository.save(studentGroup);
            } else {
                throw new EntityDoesNotExistException("Class does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Course does not exist!");
        }
    }
    public void linkModuleAndStudent(String idModule, String idStudent) {
        if(moduleRepository.existsById(Long.getLong(idModule))) {
            // module exists
            if (userRepository.existsById(Long.getLong(idStudent))) {
                // user exists
                AppUser user = userRepository.getById(Long.getLong(idStudent));
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal()))) {
                    // user is a student
                    user.getModules().add(moduleRepository.getById(Long.getLong(idModule)));
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
    public void linkSubjectAndCourse(String idSubject, String idCourse) {
        if(subjectRepository.existsById(Long.getLong(idSubject))) {
            // subject exists
            if(courseRepository.existsById(Long.getLong(idCourse))) {
                // course exists
                Course course = courseRepository.getById(Long.getLong(idCourse));
                course.setSubject(subjectRepository.getById(Long.getLong(idSubject)));
                courseRepository.save(course);
            } else {
                throw new EntityDoesNotExistException("Course does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }
    public void linkSubjectAndModule(String idSubject, String idModule) {
        if(subjectRepository.existsById(Long.getLong(idSubject))) {
            // subject exists
            if(moduleRepository.existsById(Long.getLong(idModule))) {
                // module exists
                Module module = moduleRepository.getById(Long.getLong(idModule));
                module.getSubjects().add(subjectRepository.getById(Long.getLong(idSubject)));
                moduleRepository.save(module);
            } else {
                throw new EntityDoesNotExistException("Module does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }
    public void linkSubjectAndStudent(String idSubject, String idStudent) {
        if(subjectRepository.existsById(Long.getLong(idSubject))) {
            // subject exists
            if(userRepository.existsById(Long.getLong(idStudent))) {
                // user exists
                AppUser user = userRepository.getById(Long.getLong(idStudent));
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal()))) {
                    // user is a student
                    user.getSubjects().add(subjectRepository.getById(Long.getLong(idSubject)));
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
    public void linkTeacherAndCourse(String idCourse, String idTeacher) {
        if(courseRepository.existsById(Long.getLong(idCourse))) {
            // course exists
            if(userRepository.existsById(Long.getLong(idTeacher))) {
                // user exists
                AppUser user = userRepository.getById(Long.getLong(idTeacher));
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal()))) {
                    // user is a teacher
                    Course course = courseRepository.getById(Long.getLong(idCourse));
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
