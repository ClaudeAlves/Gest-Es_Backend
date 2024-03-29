package dev.claude.service;

import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.security.BlacklistTokenEntity;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.domain.user.Role;
import dev.claude.repository.organisation.CourseRepository;
import dev.claude.repository.organisation.StudentGroupRepository;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.security.JwtUtils;
import dev.claude.security.UserDetailsImpl;
import dev.claude.service.exception.*;
import dev.claude.service.security.BlacklistTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service @Slf4j
public class UserService implements UserDetailsService {

    /**
     * Admin user found in the file application.properties.
     */
    @Component
    private static class AdminUser {
        @Value("${backend.admin.username}")
        private String username;

        @Value("${backend.admin.email}")
        private String email;

        @Value("${backend.admin.password}")
        private String password;

        @Value("${backend.admin.firstname}")
        private String firstName;

        @Value("${backend.admin.lastname}")
        private String lastName;
    }

    @Autowired
    private AdminUser adminUser;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentGroupRepository studentGroupRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BlacklistTokenService blacklistTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Encrypts ones password
     * @param password password to encrypt
     * @return a string of the encrypted password
     */
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Deletes a user the user calling this must be an admin.
     * @param username username of the user to delete.
     */
    public void deleteUser(String username) {
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optUser.isPresent() && optHolder.isPresent()) {
            if(optHolder.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // holder is admin
                try {
                    userRepository.delete(optUser.get());
                } catch (Error e) {
                    e.printStackTrace();
                    throw new InternalErrorException(e.getMessage());
                }
            } else {
                throw new UnauthorizedException("Can't delete user if not admin");
            }
        }
    }
    /**
     * Adds a role (that is valid with the DB) to a User. This is the ONLY
     * ALLOWED WAY to add a role to a user. This does not save the user to DB !
     *
     * @param user user to add a role to
     * @param roleName role to add to the suer
     * @return a new user instance with the role added
     */
    public AppUser addRole(AppUser user, EnumRole roleName) {
        log.trace("Adding role {} to user {}", roleName.toString(), user.getUsername());

        Role role = roleService.getRole(roleName);
        return user.toBuilder().role(role).build();
    }

    /**
     * Loads a user for security purpose.
     * @param username name of the user
     * @return a user special for security
     * @throws UsernameNotFoundException when the username isn't found in DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        try {
            return UserDetailsImpl.build(user);
        } catch (Exception e) {
            logger.error("Error with loadByUsername : {}", e.getMessage());
            return null;
        }

    }

    /**
     * Retrieves all the users
     * @return a list of users
     */
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Register a User if the parameters are correct.
     *
     * @param user the incomplete user without the role and with non encrypted
     *                   password the roles are added after.
     * @throws EntityAlreadyExistException if the username or email is taken
     * @throws InternalErrorException      when something bad happens
     */
    public void register(@NotNull AppUser user) throws EntityAlreadyExistException, InternalErrorException {
        if (userRepository.existsByUsername(user.getUsername())) {
            log.trace("Register username already exists {}", user.getUsername());
            throw new EntityAlreadyExistException("Username is already taken !");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            log.trace("Register email already exists {}", user.getEmail());
            throw new EntityAlreadyExistException("Email is already taken !");
        } else if (userRepository.existsById(user.getIdUser())) {
            log.trace("Register id already exists {}", user.getIdUser());
            throw new EntityAlreadyExistException("Id is already taken !");
        } else {
            // Here we have a unique email and username.
            // Check for password requirements here if needed

            try {
                // Encrypt Password
                user = user.withPassword(encryptPassword(user.getPassword()));
                user = addRole(user, EnumRole.ROLE_USER);

                // Save the user to DB
                userRepository.save(user);
                log.info("Successful register for {}", user);

            } catch (Exception e) {
                if (userRepository.existsByUsername(user.getUsername())) {
                    log.info("Successful register for {}", user.getUsername());
                } else {

                    e.printStackTrace();
                    log.error("{}", e.getMessage());
                    throw new InternalErrorException(e.getMessage());
                }
            }
        }
    }

    /**
     * Retrieves the user from the database.
     * @param username username of the user to retrieve
     * @return the user retrieved
     */
    public AppUser getUser(String username) {
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        if( optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new EntityDoesNotExistException("user doesn't exist");
        }
    }

    /**
     * This function updates a user.
     * @param user user to update
     * @return the modified user
     */
    public AppUser updateUser(AppUser user) {
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optHolder.isPresent()) {
            if(optHolder.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))) {
                // holder is admin
                try {
                    userRepository.save(user);
                    return user;
                } catch (Error e) {
                    e.printStackTrace();
                    throw new InternalErrorException(e.getMessage());
                }
            } else {
                throw new UnauthorizedException("Can't update user if not admin");
            }
        } else {
            throw new RuntimeException("No context Holder");
        }
    }
    /**
     *  This function tries to log a user given a username or email and a password.
     * @param usernameOrEmail username or email used to try to connect
     * @param password password used to try to connect
     * @return the JSON web token
     * @throws WrongCredentialsException when wrong credentials are given
     * @throws InternalErrorException when something bad happens
     */
    public String login(String usernameOrEmail, String password)
            throws WrongCredentialsException, InternalErrorException {

        if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
            throw new IncompleteBodyException();
        }

        try {
            AppUser user = null;

            try {
                logger.info("Login find by email {}", usernameOrEmail);
                user = userRepository.findByEmail(usernameOrEmail).orElseThrow();
            } catch (NoSuchElementException e) {
                // It's not an email it must be the username
                logger.info("Login find by email {} no such element, trying to find by username.", usernameOrEmail);
                user = userRepository.findByUsername(usernameOrEmail).orElseThrow();
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            logger.info("Successful login by {}", usernameOrEmail);

            return jwt;

        } catch (NoSuchElementException e) {
            log.trace("Login find by username {} no such element. Throwing NoSuchElementException.",
                    usernameOrEmail);
            logger.info("Login attempt by {} - username ou email does not exist.", usernameOrEmail);
            throw new WrongCredentialsException();
        } catch (BadCredentialsException e) {
            log.trace("Login bad password provided for existing user {}", usernameOrEmail);
            logger.info("Login attempt by {} with wrong credentials", usernameOrEmail);
            throw new WrongCredentialsException();
        } catch (Exception e) {
            logger.error("Internal Error, original exception message : {}", e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }
    }
    /**
     * Returns the current user of the request from the DB.
     *
     * @return the current user of the request.
     * @throws EntityDoesNotExistException when something bad happens
     */
    public AppUser getCurrentUser() throws EntityDoesNotExistException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optUser = userRepository.findByUsername(username);
        return optUser.orElseThrow(()-> new EntityDoesNotExistException("Current user cannot be retrieved from JWT"));
    }

    /**
     * Logs out a user by placing the JWT to the TokenBlacklist.
     *
     * //@param jwtHeader the header containing the Bearer JWT
     * @throws InternalErrorException when something bad happens
     */
    public void logout() throws InternalErrorException {
        try {

            String jwt = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            log.info("JWT to logout : {}", jwt);
            BlacklistTokenEntity blacklistToken = BlacklistTokenEntity.builder()
                    .token(jwt)
                    .expiration(jwtUtils.getExpirationTime(jwt))
                    .build();

            blacklistTokenService.save(blacklistToken);
            log.info("Logout successful for user");
        } catch (ExpiredJwtException e) {
            log.warn("Logout attempt with expired token");
            throw new EntityAlreadyExistException("You are already logged out. The Token has expired.");
        } catch (Exception e) {
            log.error("Internal Error, original exception message : {}", e.getMessage());
            throw new InternalErrorException();
        }
    }
    /**
     * Creates the admin user stored in the application.properties file.
     * @throws InternalErrorException when something bad happens
     */
    public void createAdminUser() throws InternalErrorException {
        try {
            if (userRepository.existsByUsername(adminUser.username)) {
                log.info("Existing admin user, with username: {} password: {}", adminUser.username,
                        adminUser.password);

            } else {
                log.info("Creating admin user, with username: {} password: {}", adminUser.username,
                        adminUser.password);

                AppUser user = AppUser.builder()
                        .username(adminUser.username)
                        .email(adminUser.email)
                        .password(encryptPassword(adminUser.password))
                        .firstName(adminUser.firstName)
                        .lastName(adminUser.lastName)
                        .build();

                user = addRole(user, EnumRole.ROLE_USER);
                user = addRole(user, EnumRole.ROLE_ADMIN);
                userRepository.save(user);

            }

        }
        catch (Exception e) {
            log.error("Internal Error, original exception message : {}", e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }
    }

    /**
     * Get the courses of the user.
     * @return a list of courses
     */
    public List<Course> getCourses() {
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optHolder.isPresent()) {
            AppUser currentUser = optHolder.get();
            List<Course> courses;
            if(currentUser.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                courses = courseRepository.findAllDistinctByStudentGroups_Students_IdUser(currentUser.getIdUser());
            } else if(currentUser.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal() + 1))) {
                courses = courseRepository.findAllByTeacher_IdUser(currentUser.getIdUser());
            } else if(currentUser.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))){
                courses = courseRepository.findAll();
            } else {
                throw new UnauthorizedException("role can't have courses");
            }
            return courses;
        } else {
            throw new RuntimeException("No context Holder");
        }
    }

    /**
     * Get the classes for the user.
     * The classes returned depends on the user, it only returns the classes in link with the user.
     * @return a list of classes
     */
    public List<StudentGroup> getStudentGroups() {
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optHolder.isPresent()) {
            AppUser currentUser = optHolder.get();
            List<StudentGroup> studentGroups;
            if(currentUser.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                studentGroups = studentGroupRepository.findAllByStudents_IdUser(currentUser.getIdUser());
            } else if(currentUser.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_TEACHER.ordinal() + 1))) {
                studentGroups = studentGroupRepository.findAllByCourses_Teacher_IdUser(currentUser.getIdUser());
            } else if(currentUser.getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_ADMIN.ordinal() + 1))){
                studentGroups = studentGroupRepository.findAll();
            } else {
                throw new UnauthorizedException("role can't have class");
            }
            return studentGroups;
        } else {
            throw new RuntimeException("No context Holder");
        }
    }

    /**
     * Retrieves the students following a specific course.
     * @param courseId course id
     * @return a list of students
     */
    public List<AppUser> getStudentsFromCourseId(Long courseId) {
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optHolder.isPresent()) {
            if(!optHolder.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                return userRepository.findAllByStudentGroups_Courses_IdCourse(courseId);
            } else {
                throw new UnauthorizedException("Student not allowed");
            }
        } else {
            throw new RuntimeException("No context Holder");
        }

    }

    /**
     * Retrieves the classes that follow a specific course.
     * @param idCourse course id
     * @return a list of classes
     */
    public List<StudentGroup> getClassFromCourse(Long idCourse) {
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optHolder.isPresent()) {
            if(!optHolder.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                return studentGroupRepository.findAllByCourses_IdCourse(idCourse);
            } else {
                throw new UnauthorizedException("Student not allowed");
            }
        } else {
            throw new RuntimeException("No context Holder");
        }

    }

    /**
     * Gets the students for a specific test.
     * @param idTest test id
     * @return a list of students
     */
    public List<AppUser> getStudentsFromTest(Long idTest) {
        String holderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> optHolder = userRepository.findByUsername(holderUsername);
        if(optHolder.isPresent()) {
            if(!optHolder.get().getRoles().contains(roleRepository.getById((long) EnumRole.ROLE_STUDENT.ordinal() + 1))) {
                return userRepository.findAllByStudentGroups_Tests_IdTest(idTest);
            } else {
                throw new UnauthorizedException("Students not allowed");
            }
        } else {
            throw new RuntimeException("No context Holder");
        }

    }




}
