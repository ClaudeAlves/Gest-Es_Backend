package dev.claude.config;

import dev.claude.service.RoleService;
import dev.claude.service.UserService;
import dev.claude.service.security.BlacklistTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

/**
 * Configuration setup about periodical events and after startup actions
 */
@Configuration
@EnableScheduling
public class ScheduledEvents {
    @Autowired
    private BlacklistTokenService blacklistTokenService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Scheduled(fixedDelayString = "${backend.schedule.TokenBlacklistClean}")
    public void deleteBlackListedTokens() {
        blacklistTokenService.deleteExpired();
    }

    @PostConstruct
    public void runAfterStartup() {
        roleService.generateRolesToDatabase();
        userService.createAdminUser();
    }



}
