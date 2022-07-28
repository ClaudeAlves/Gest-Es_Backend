package dev.claude.service.security;

import dev.claude.domain.security.BlacklistTokenEntity;
import dev.claude.repository.security.BlacklistTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * This service is here to manage the token blacklist some of those functions are called periodically
 * by the server see in config folder "ScheduledEvents"
 */
@Slf4j
@Service
public class BlacklistTokenService {
    @Autowired
    BlacklistTokenRepository repository;

    /**
     * Saves a JWT to the blacklist tokens.
     * @param blacklistToken token to add
     */
    public void save(BlacklistTokenEntity blacklistToken) {
        try {
            repository.save(blacklistToken);
        } catch (DataIntegrityViolationException e) {
            log.warn("The token with id {} is already in the blacklist !", blacklistToken.getId());
        }
    }

    /**
     * Deletes a JWT from the blacklist.
     * @param blacklistToken token to delete
     */
    public void delete(BlacklistTokenEntity blacklistToken) {
        repository.delete(blacklistToken);
    }

    /**
     * Gets a token from the blacklist.
     * @param token token to get
     * @return an optional token can be empty if not found
     */
    public Optional<BlacklistTokenEntity> get(String token) {
        return repository.findByToken(token);
    }

    /**
     * Deletes expired tokens.
     */
    public void deleteExpired() {
        Date now = new Date();
        Iterable<BlacklistTokenEntity> tokens = repository.findAll();
        tokens.forEach(t -> {
            // If the token is expired
            if (t.getExpiration() < now.getTime()) {
                delete(t);
            }
        });
    }

}
