package dev.claude.service.security;

import dev.claude.domain.security.BlacklistTokenEntity;
import dev.claude.repository.security.BlacklistTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class BlacklistTokenService {
    @Autowired
    BlacklistTokenRepository repository;

    public void save(BlacklistTokenEntity blacklistToken) {
        try {
            repository.save(blacklistToken);
        } catch (DataIntegrityViolationException e) {
            log.warn("The token with id {} is already in the blacklist !", blacklistToken.getId());
        }
    }

    public void delete(BlacklistTokenEntity blacklistToken) {
        repository.delete(blacklistToken);
    }

    public Optional<BlacklistTokenEntity> get(String token) {
        return repository.findByToken(token);
    }

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
