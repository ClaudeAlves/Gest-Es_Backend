package dev.claude.service;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.evalutation.Mark;
import dev.claude.domain.evalutation.Test;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.Module;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.dto.*;
import dev.claude.mapper.evaluation.TestMapper;
import dev.claude.repository.calendar.PeriodRepository;
import dev.claude.repository.evaluation.MarkRepository;
import dev.claude.repository.evaluation.TestRepository;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.ModuleRepository;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.service.exception.EntityDoesNotExistException;
import dev.claude.service.exception.InternalErrorException;
import dev.claude.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    MarkRepository markRepository;
    @Autowired
    TestMapper testMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StudentGroupRepository studentGroupRepository;
    @Autowired
    PeriodRepository periodRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(EvaluationService.class);

    /**
     * Creates a test, and saves it to the DB.
     * @param test not completely filled test
     * @param dto test DTO
     */
    public void createTest(Test test, TestDTO dto) {
        Optional<Course> optCourse = courseRepository.findById(dto.getCourseId());
        if(optCourse.isPresent()) {
            int testNumber = testRepository.countAllByPeriod_Course_IdCourse(dto.getCourseId()) + 1;
            Period testPeriod = Period.builder()
                    .course(optCourse.get())
                    .end(dto.getEnd().toLocalDateTime())
                    .start(dto.getStart().toLocalDateTime())
                    .text(dto.getText())
                    .tag("test "+ testNumber + " pour " + optCourse.get().getName())
                    .build();
            periodRepository.save(testPeriod);
            test.setPeriod(testPeriod);
            test.setNumber(testNumber);
            test.setStudentGroups(new LinkedList<>());
            for (StudentGroup studentGroup : studentGroupRepository.findAllByCourses_IdCourse(dto.getCourseId())) {
                // here we link every class(student group) to the new test created
                test.getStudentGroups().add(studentGroup);
            }
            testRepository.save(test);
            for(StudentGroup studentGroup : test.getStudentGroups()) {
                studentGroup.getTests().add(test);
                studentGroupRepository.save(studentGroup);
            }
        } else {
            throw new EntityDoesNotExistException("Course doesn't exist");
        }
    }

    /**
     * Get the grades of a student.
     * @param idStudent id of the student
     * @return a list of grade DTOs
     */
    public List<GradeDTO> getGradesFromStudentId(Long idStudent) {
        List<Course> courses = courseRepository.findAllDistinctByStudentGroups_Students_IdUser(idStudent);
        List<GradeDTO> grades = new LinkedList<>();
        for(Course course : courses) {
            Double grade = 0D;
            double testNumber = 0D;
            for(Mark mark : markRepository.findAllByTest_Period_Course_IdCourse(course.getIdCourse())) {
                grade += mark.getValue();
                testNumber++;
            }
            GradeDTO gradeDTO = new GradeDTO();
            gradeDTO.setCourseName(course.getName());
            gradeDTO.setValue(grade/testNumber);
            grades.add(new GradeDTO());
        }
        return grades;
    }

    /**
     * Gets the tests of a courses for a specific student.
     * @param idCourse id of the course
     * @param idStudent id of the student
     * @return a list of test DTOs
     */
    public List<TestDTO> getCourseTestsFromStudentId(Long idCourse, Long idStudent) {
        List<TestDTO> tests = new LinkedList<>();
        Optional<Course> optCourse = courseRepository.findById(idCourse);
        if (optCourse.isPresent()) {
            for(Test test : testRepository.findAllByPeriod_Course_StudentGroups_Students_IdUser(idStudent)) {
                tests.add(testMapper.toDto(test));
            }
        } else {
            throw new EntityDoesNotExistException("Course doesn't exist");
        }
        return tests;
    }

    /**
     * Notes a test.
     * @param mark mark to set the value
     * @param idStudent id of the student
     * @param idTest id of the test
     */
    public void noteStudent(Mark mark, Long idStudent, Long idTest) {
        Optional<AppUser> optStudent = userRepository.findById(idStudent);
        Optional<Test> optTest = testRepository.findById(idTest);
        if(optStudent.isEmpty()) {
            throw new EntityDoesNotExistException("Student doesn't exist");
        } else if (optTest.isEmpty()){
            throw new EntityDoesNotExistException("Test doesn't exist");
        } else if(markRepository.existsByTest_IdTestAndStudent_IdUser(idTest, idStudent)) {
            Double oldValue = mark.getValue();
            mark = markRepository.findByTest_IdTestAndStudent_IdUser(idTest, idStudent);
            mark.setValue(oldValue);
            markRepository.save(mark);
        } else {
            mark.setStudent(optStudent.get());
            mark.setTest(optTest.get());
            markRepository.save(mark);
        }

    }

    /**
     * Get the tests of the current user.
     * @return a list of tests
     */
    public List<Test> getSelfTests() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if (!optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                // user is not a student
                return getTestsFromUserId(optUser.get().getIdUser(), optUser);
            } else {
                throw new EntityDoesNotExistException("Student can't get tests");
            }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
    }

    /**
     * Gets the tests of a specific user.
     * @param idUser id of the user
     * @return a list of tests
     */
    public List<Test> getTests(Long idUser) {
        Optional<AppUser> optUser = userRepository.findById(idUser);
        if(optUser.isPresent()) {
            return getTestsFromUserId(idUser, optUser);
        } else {
            throw new EntityDoesNotExistException("User doesn't exist in DB");
        }
    }

    /**
     * Gets the tests of a specific class.
     * @param idStudentGroup id of the class
     * @return a list of tests
     */
    public List<Test> getStudentGroupTests(Long idStudentGroup) {
        Optional<StudentGroup> optGroup = studentGroupRepository.findById(idStudentGroup);
        if(optGroup.isPresent()) {
            return testRepository.findAllByPeriod_Course_StudentGroups_IdStudentGroup(idStudentGroup);
        } else {
            throw new EntityDoesNotExistException("Student group doesn't exist in DB");
        }
    }
    private List<Test> getTestsFromUserId(Long idUser, Optional<AppUser> optUser) {
        List<Test> tests;
        if (optUser.isPresent() && optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
            // user is an admin he gets all the tests
            tests =  testRepository.findAll();
        } else if (optUser.isPresent() && optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal() + 1))) {
            // user is a teacher
            tests = testRepository.findAllByPeriod_Course_Teacher_IdUser(idUser);
        } else {
            throw new UnauthorizedException("Students can't access tests");
        }
        try {
            return tests;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
    }

    /**
     * Returns displayable test infos for the current user.
     * @return DTO test infos
     */
    public List<TestInfoDTO> getTestsInfoUsers() {
        List<TestInfoDTO> testInfoDTOS = new LinkedList<>();
        for(Test test : getSelfTests()) {
            TestInfoDTO testInfoDTO = new TestInfoDTO();
            List<TestInfoDTOStudents> testInfoDTOStudents = new LinkedList<>();
            for(AppUser student : userService.getStudentsFromTest(test.getIdTest())) {
                Optional<Mark> mark = markRepository.findByTestAndStudent(test, student);
                TestInfoDTOStudents studentDTO = new TestInfoDTOStudents();
                studentDTO.setName(student.getUsername());
                studentDTO.setId(student.getIdUser());
                if(mark.isPresent()) {
                    studentDTO.setTestValue(mark.get().getValue());
                } else {
                    studentDTO.setTestValue(null);
                }
                testInfoDTOStudents.add(studentDTO);

            }
            testInfoDTO.setWeighting(test.getWeighting());
            testInfoDTO.setStudents(testInfoDTOStudents);
            testInfoDTO.setCourseId(test.getPeriod().getCourse().getIdCourse());
            testInfoDTO.setTestName(test.getPeriod().getTag());
            testInfoDTO.setTestId(test.getIdTest());
            testInfoDTO.setText(test.getText());
            testInfoDTOS.add(testInfoDTO);
        }
        return testInfoDTOS;
    }

    /**
     * Gets displayable infos for a module for a specific class.
     * @param idModule id of the module
     * @param idStudentGroup id of the class
     * @return module marks infos
     */
    public ModuleMarkInfos getModuleInfosByClass(Long idModule, Long idStudentGroup) {
        ModuleMarkInfos moduleInfos = new ModuleMarkInfos();
        List<ModuleMarkInfosMarks> marksInfos = new LinkedList<>();
        List<ModuleMarkInfosTests> testsInfos = new LinkedList<>();
        List<ModuleMarkInfosSubjects> subjectsInfos = new LinkedList<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if(!optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                // user is not a student
                if (moduleRepository.existsById(idModule) && studentGroupRepository.existsById(idStudentGroup)) {

                    Module module = moduleRepository.getById(idModule);
                    moduleInfos.setModuleId(module.getIdModule());
                    moduleInfos.setModuleName(module.getName());

                    List<Course> courses = courseRepository.findAllBySubject_Module_IdModuleAndStudentGroups_IdStudentGroup(idModule, idStudentGroup);
                    List<AppUser> students = userRepository.findAllByStudentGroups_IdStudentGroup(idStudentGroup);

                    for (AppUser student : students) {
                        ModuleMarkInfosMarks markInfoToAdd = new ModuleMarkInfosMarks();
                        markInfoToAdd.setStudentName(student.getUsername());
                        markInfoToAdd.setMarks(new LinkedList<>());
                        List<Mark> moduleMarks = markRepository.findAllDistinctByTest_Period_Course_Subject_Module_IdModuleAndStudent_IdUser(idModule, student.getIdUser());
                        setModuleMean(marksInfos, markInfoToAdd, moduleMarks);

                    }
                    for (Course course : courses) {
                        ModuleMarkInfosSubjects subjectToAdd = new ModuleMarkInfosSubjects();
                        subjectToAdd.setSubjectName(course.getSubject().getName());
                        Integer numberOfMarks = 0;
                        for (Test test : testRepository.findAllByPeriod_Course_IdCourse(course.getIdCourse())) {
                            ModuleMarkInfosTests testInfoToAdd = new ModuleMarkInfosTests();
                            testInfoToAdd.setTestName(test.getPeriod().getTag());
                            testInfoToAdd.setDate(test.getPeriod().getStart().getDayOfMonth() + " " + test.getPeriod().getStart().getMonth().toString());
                            testsInfos.add(testInfoToAdd);
                            numberOfMarks++;
                            for (AppUser student : students) {
                                if(markRepository.existsByTestAndStudent(test, student)) {
                                    for (ModuleMarkInfosMarks item : marksInfos) {
                                        if(item.getStudentName().equals(student.getUsername())) {
                                            marksInfos.set(marksInfos.indexOf(item), item.addMarksItem(markRepository.findByTestAndStudent(test, student).get().getValue()));
                                        }
                                    }
                                } else {
                                    for (ModuleMarkInfosMarks item : marksInfos) {
                                        if(item.getStudentName().equals(student.getUsername())) {
                                            marksInfos.set(marksInfos.indexOf(item), item.addMarksItem(null));
                                        }
                                    }
                                }
                            }
                        }
                        subjectToAdd.setMarksNumber(numberOfMarks);
                        subjectsInfos.add(subjectToAdd);
                    }
                    moduleInfos.setMarks(marksInfos);
                    moduleInfos.setSubjects(subjectsInfos);
                    moduleInfos.setTests(testsInfos);
                } else {
                    throw new EntityDoesNotExistException("Module or class doesn't exist");
                }
            } else {
                throw new UnauthorizedException("Students can't access other students infos");
            }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
        return moduleInfos;
    }

    /**
     * Gets the modules of a specific class.
     * @param idClass id of the class to get the modules of
     * @return a list of modules
     */
    public List<Module> getModulesByClass(Long idClass) {
        List<Module> modules = new LinkedList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if(studentGroupRepository.existsById(idClass)) {
                StudentGroup studentGroup = studentGroupRepository.getById(idClass);
                for( Course course : studentGroup.getCourses()) {
                    modules.add(course.getSubject().getModule());
                }
            } else {
                throw new EntityDoesNotExistException("Class not found");
            }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
        return modules;
    }

    /**
     * Gets the modules for the current user.
     * @return a list of modules
     */
    public List<Module> getSelfModules() {
        List<Module> modules = new LinkedList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if(optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                List<Course> courses = courseRepository.findAllDistinctByStudentGroups_Students_IdUser(optUser.get().getIdUser());
                for( Course course : courses) {
                    modules.add(course.getSubject().getModule());
                }
            } else {
                throw new UnauthorizedException("Only students allowed for this");
            }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
        return modules;
    }

    /**
     * Calculates the grades for a specific class.
     * @param idStudentGroup id of the class to get the synthesis from
     * @return returns a synthesis of the grades
     */
    public GradeSynthesis getSynthesisByClass(Long idStudentGroup) {
        GradeSynthesis gradeSynthesis = new GradeSynthesis();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if (!optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                if (studentGroupRepository.existsById(idStudentGroup)) {
                    List<AppUser> students = userRepository.findAllByStudentGroups_IdStudentGroup(idStudentGroup);
                    StudentGroup studentGroup = studentGroupRepository.getById(idStudentGroup);
                    List<Module> modules = new LinkedList<>();
                    studentGroup.getCourses().forEach(course -> {
                        if (!modules.contains(course.getSubject().getModule())) {
                            modules.add(course.getSubject().getModule());
                        }
                    });
                    for (Module module : modules) {
                        gradeSynthesis.addModulesItem(module.getName());
                    }
                    for (AppUser student : students) {
                        GradeSynthesisStudentMarks studentMarks = new GradeSynthesisStudentMarks();
                        studentMarks.setStudentName(student.getUsername());
                        double globalMean = 0.;
                        double modulesNotMarked = 0;
                        for(Module module : modules) {
                            List<Mark> moduleMarks = markRepository.findAllDistinctByTest_Period_Course_Subject_Module_IdModuleAndStudent_IdUser(module.getIdModule(), student.getIdUser());
                            double meanToAdd = getGlobalMean(studentMarks, globalMean, moduleMarks);
                            if (meanToAdd == globalMean) {
                                modulesNotMarked++;
                            }
                            globalMean = meanToAdd;
                        }
                        studentMarks.setGlobalMean(globalMean/(modules.size()-modulesNotMarked));
                        gradeSynthesis.addStudentMarksItem(studentMarks);
                    }
                } else {
                    throw new EntityDoesNotExistException("Class doesn't exist");
                }
            } else {
                throw new UnauthorizedException("Students can't access others students infos");
            }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
        return gradeSynthesis;
    }

    /**
     * Gets displayable infos for a specific module of the current user.
     * @param idModule id of the module to get infos of
     * @return module marks infos
     */
    public ModuleMarkInfos getSelfModuleInfos(Long idModule) {
        ModuleMarkInfos moduleInfos = new ModuleMarkInfos();
        List<ModuleMarkInfosMarks> marksInfos = new LinkedList<>();
        List<ModuleMarkInfosTests> testsInfos = new LinkedList<>();
        List<ModuleMarkInfosSubjects> subjectsInfos = new LinkedList<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
            if(optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                // user is a student
                if (moduleRepository.existsById(idModule)) {
                    Module module = moduleRepository.getById(idModule);
                    moduleInfos.setModuleId(module.getIdModule());
                    moduleInfos.setModuleName(module.getName());

                    ModuleMarkInfosMarks markInfoToAdd = new ModuleMarkInfosMarks();
                    markInfoToAdd.setStudentName(optUser.get().getUsername());
                    markInfoToAdd.setMarks(new LinkedList<>());
                    List<Mark> moduleMarks = markRepository.findAllDistinctByTest_Period_Course_Subject_Module_IdModuleAndStudent_IdUser(idModule, optUser.get().getIdUser());
                    List<Course> courses = courseRepository.findAllBySubject_Module_IdModuleAndStudentGroups_Students_IdUser(idModule, optUser.get().getIdUser());
                    setModuleMean(marksInfos, markInfoToAdd, moduleMarks);
                    for (Course course : courses) {
                        ModuleMarkInfosSubjects subjectToAdd = new ModuleMarkInfosSubjects();
                        subjectToAdd.setSubjectName(course.getSubject().getName());
                        Integer numberOfMarks = 0;
                        for (Test test : testRepository.findAllByPeriod_Course_IdCourse(course.getIdCourse())) {
                            ModuleMarkInfosTests testInfoToAdd = new ModuleMarkInfosTests();
                            testInfoToAdd.setTestName(test.getPeriod().getTag());
                            testInfoToAdd.setDate(test.getPeriod().getStart().getDayOfMonth() + " " + test.getPeriod().getStart().getMonth().toString());
                            testsInfos.add(testInfoToAdd);
                            numberOfMarks++;

                            if(markRepository.existsByTestAndStudent(test, optUser.get())) {
                                for (ModuleMarkInfosMarks item : marksInfos) {
                                    if(item.getStudentName().equals(optUser.get().getUsername())) {
                                        marksInfos.set(marksInfos.indexOf(item), item.addMarksItem(markRepository.findByTestAndStudent(test, optUser.get()).get().getValue()));
                                    }
                                }
                            } else {
                                for (ModuleMarkInfosMarks item : marksInfos) {
                                    if(item.getStudentName().equals(optUser.get().getUsername())) {
                                        marksInfos.set(marksInfos.indexOf(item), item.addMarksItem(null));
                                    }
                                }
                            }

                        }
                        subjectToAdd.setMarksNumber(numberOfMarks);
                        subjectsInfos.add(subjectToAdd);
                    }
                    moduleInfos.setMarks(marksInfos);
                    moduleInfos.setSubjects(subjectsInfos);
                    moduleInfos.setTests(testsInfos);


                } else {
                    throw new EntityDoesNotExistException("Module doesn't exist");
                }
            } else {
                throw new UnauthorizedException("Only students can have their students infos");
            }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
        return moduleInfos;
    }

    /**
     * Calculates the grades for the current student.
     * @return a synthesis of the grades
     */
    public GradeSynthesis getSelfSynthesis() {
        GradeSynthesis gradeSynthesis = new GradeSynthesis();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()) {
           if (optUser.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
               // user is a student

               optUser.get().getModules().forEach( module -> {
                   gradeSynthesis.addModulesItem(module.getName());
               });

               GradeSynthesisStudentMarks studentMarks = new GradeSynthesisStudentMarks();
               studentMarks.setStudentName(username);
               double globalMean = 0.;
               double modulesNotMarked = 0;
               for(Module module : optUser.get().getModules()) {
                   List<Mark> moduleMarks = markRepository.findAllDistinctByTest_Period_Course_Subject_Module_IdModuleAndStudent_IdUser(module.getIdModule(), optUser.get().getIdUser());
                   double meanToAdd = getGlobalMean(studentMarks, globalMean, moduleMarks);
                   if ( meanToAdd == globalMean) {
                       modulesNotMarked++;
                   }
                   globalMean = meanToAdd;
               }
               studentMarks.setGlobalMean(globalMean/(optUser.get().getModules().size()-modulesNotMarked));
               gradeSynthesis.addStudentMarksItem(studentMarks);
           } else {
               throw new UnauthorizedException("Only students can have their grades synthesis");
           }
        } else {
            throw new EntityDoesNotExistException("Context holder not found");
        }
        return gradeSynthesis;
    }

    /**
     * Computes the mean of a module.
     * @param marksInfos list of markInfo items to be added to
     * @param markInfoToAdd markInfo to add to the markInfo list
     * @param moduleMarks list of marks
     */
    private void setModuleMean(List<ModuleMarkInfosMarks> marksInfos, ModuleMarkInfosMarks markInfoToAdd, List<Mark> moduleMarks) {
        double mean = 0.;
        int marksUnderMean = 0;
        double coefs = 0.;
        double div;
        for (Mark mark : moduleMarks) {
            mean += mark.getValue()*mark.getTest().getWeighting();
            coefs += mark.getTest().getWeighting();
            if (mark.getValue() < 4) {
                marksUnderMean++;
            }
        }
        markInfoToAdd.setModuleMean(Math.round(mean / coefs * 10) / 10.0);

        markInfoToAdd.setMarksUnderMean(marksUnderMean + "/" + moduleMarks.size());
        marksInfos.add(markInfoToAdd);
    }

    /**
     * Modifies the global mean of a student with the marks of a module.
     * @param studentMarks object of marks
     * @param globalMean global mean to modify
     * @param moduleMarks list of marks
     * @return modified global mean
     */
    private double getGlobalMean(GradeSynthesisStudentMarks studentMarks, double globalMean, List<Mark> moduleMarks) {
        GradeSynthesisMarks markToAdd = new GradeSynthesisMarks();
        double mean = 0.;
        Integer marksUnderMean = 0;
        double coefs = 0.;
        for (Mark mark : moduleMarks) {
            mean += mark.getValue()*mark.getTest().getWeighting();
            coefs += mark.getTest().getWeighting();
            if (mark.getValue() < 4) {
                marksUnderMean++;
            }
        }
        mean = Math.round(mean / coefs * 10) / 10.0;
        markToAdd.setMean(mean);
        markToAdd.setMarksUnderMean(marksUnderMean + "/" + moduleMarks.size());
        studentMarks.addMarksItem(markToAdd);
        globalMean+=mean;
        return globalMean;
    }
}
