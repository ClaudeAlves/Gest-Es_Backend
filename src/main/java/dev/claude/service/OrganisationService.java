package dev.claude.service;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.Module;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.organisation.Subject;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.repository.calendar.PeriodRepository;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.ModuleRepository;
import dev.claude.repository.organisation.SubjectRepository;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.security.AuthTokenFilter;
import dev.claude.service.exception.EntityDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private PeriodRepository periodRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    /**
     * Link a class and a student.
     * @param idClass id of the class
     * @param idStudent id of the student
     */
    public void linkClassAndStudent(Long idClass, Long idStudent) {
        if(studentGroupRepository.existsById(idClass)) {
            // class exists
            if (userRepository.existsById(idStudent)) {
                // user exists
                AppUser user = userRepository.getById(idStudent);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                    // user is a student
                    StudentGroup studentGroup = studentGroupRepository.getById(idClass);
                    if(!user.getStudentGroups().contains(studentGroup)) {
                        // not already linked studentGroup -> student
                        user.getStudentGroups().add(studentGroup);
                        userRepository.save(user);
                    }
                    if(!studentGroup.getStudents().contains(user)){
                        // not already linked student -> studentGroup
                        studentGroup.getStudents().add(user);
                        studentGroupRepository.save(studentGroup);
                    }
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

    /**
     * Links a course and a class.
     * @param idCourse id of the course
     * @param idClass id of the class
     */
    public void linkCourseAndClass(Long idCourse, Long idClass) {
        if(courseRepository.existsById(idCourse)) {
            // course exists
            if(studentGroupRepository.existsById(idClass)) {
                // class exists
                StudentGroup studentGroup = studentGroupRepository.getById(idClass);
                Course course = courseRepository.getById(idCourse);
                if(!studentGroup.getCourses().contains(course)) {
                    // not already linked course -> studentGroup
                    studentGroup.getCourses().add(course);
                    studentGroupRepository.save(studentGroup);
                }
                if(!course.getStudentGroups().contains(studentGroup)) {
                    // not already linked studentGroup -> course
                    course.getStudentGroups().add(studentGroup);
                    courseRepository.save(course);
                }
            } else {
                throw new EntityDoesNotExistException("Class does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Course does not exist!");
        }
    }

    /**
     * Links a module and a student.
     * @param idModule id of the module
     * @param idStudent id of the student
     */
    public void linkModuleAndStudent(Long idModule, Long idStudent) {
        if(moduleRepository.existsById(idModule)) {
            // module exists
            if (userRepository.existsById(idStudent)) {
                // user exists
                AppUser user = userRepository.getById(idStudent);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                    // user is a student
                    Module module = moduleRepository.getById(idModule);
                    for(Subject subject : module.getSubjects()) {
                        linkSubjectAndStudent(subject.getIdSubject(), idStudent);
                    }
                    if (!user.getModules().contains(module))
                    {
                        // not already linked module -> student
                        user.getModules().add(module);
                        userRepository.save(user);
                    }
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

    /**
     * Links a subject and a course.
     * @param idSubject id of the subject
     * @param idCourse id of the course
     */
    public void linkSubjectAndCourse(Long idSubject, Long idCourse) {
        if(subjectRepository.existsById(idSubject)) {
            // subject exists
            if(courseRepository.existsById(idCourse)) {
                // course exists
                Course course = courseRepository.getById(idCourse);
                course.setSubject(subjectRepository.getById(idSubject));
                // now we can add subject text and tag to each period of this course
                for (Period period : periodRepository.findAllByCourse_IdCourse(idCourse)) {
                    period.setTag(course.getSubject().getAbbreviation());
                    period.setText(course.getSubject().getName());
                    periodRepository.save(period);
                }
                courseRepository.save(course);
            } else {
                throw new EntityDoesNotExistException("Course does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }

    /**
     * Links a subject and a module.
     * @param idSubject id of the subject
     * @param idModule id of the module
     */
    public void linkSubjectAndModule(Long idSubject, Long idModule) {
        if(subjectRepository.existsById(idSubject)) {
            // subject exists
            if(moduleRepository.existsById(idModule)) {
                // module exists
                Module module = moduleRepository.getById(idModule);
                Subject subject = subjectRepository.getById(idSubject);
                if(!module.getSubjects().contains(subject)) {
                    // not already linked subject -> module
                    module.getSubjects().add(subject);
                    subject.setModule(module);
                    subjectRepository.save(subject);
                    moduleRepository.save(module);
                }
            } else {
                throw new EntityDoesNotExistException("Module does not exist!");
            }
        } else {
            throw new EntityDoesNotExistException("Subject does not exist!");
        }
    }

    /**
     * Links a subject and a student.
     * @param idSubject id of the subject
     * @param idStudent id of the student
     */
    public void linkSubjectAndStudent(Long idSubject, Long idStudent) {
        if(subjectRepository.existsById(idSubject)) {
            // subject exists
            if(userRepository.existsById(idStudent)) {
                // user exists
                AppUser user = userRepository.getById(idStudent);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                    // user is a student
                    Subject subject = subjectRepository.getById(idSubject);
                    if(!user.getSubjects().contains(subject)) {
                        user.getSubjects().add(subject);
                        userRepository.save(user);
                    }
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

    /**
     * Links a teacher and a course.
     * @param idCourse id of the course
     * @param idTeacher id of the teacher
     */
    public void linkTeacherAndCourse(Long idCourse, Long idTeacher) {
        if(courseRepository.existsById(idCourse)) {
            // course exists
            if(userRepository.existsById(idTeacher)) {
                // user exists
                AppUser user = userRepository.getById(idTeacher);
                if(user.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal() + 1))) {
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
