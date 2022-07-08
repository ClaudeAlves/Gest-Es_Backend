package dev.claude.service;

import dev.claude.domain.security.BlacklistTokenEntity;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.domain.user.Role;
import dev.claude.repository.user.RoleRepository;
import dev.claude.repository.user.UserRepository;
import dev.claude.security.JwtUtils;
import dev.claude.security.UserDetailsImpl;
import dev.claude.service.exception.*;
import dev.claude.service.security.BlacklistTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
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
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BlacklistTokenService blacklistTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Adds a role (that is valid with the DB) to a User. This is the ONLY
     * ALLOWED WAY to add a role to an user. This does not save the user to DB !
     *
     * @param user
     * @param roleName
     * @return a new user instance with the role added
     */
    public AppUser addRole(AppUser user, EnumRole roleName) {
        log.trace("Adding role {} to user {}", roleName.toString(), user.getUsername());

        Role role = roleService.getRole(roleName);
        return user.toBuilder().role(role).build();
    }

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
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
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Register a User if the parameters are correct.
     *
     * @param user the incomplete user without the role and with non encrypted
     *                   password.
     * @throws EntityAlreadyExistException if the username or email is taken
     * @throws InternalErrorException      If something bad happens.
     */
    public void register(AppUser user) throws EntityAlreadyExistException, InternalErrorException {
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
     *
     * @param usernameOrEmail
     * @param password
     * @return
     * @throws WrongCredentialsException
     * @throws InternalErrorException
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
     * @throws EntityDoesNotExistException if something bad happens.
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
     * @throws InternalErrorException If something bad happens.
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
     * Creates the main admin user with parameters from application.properties
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






}
