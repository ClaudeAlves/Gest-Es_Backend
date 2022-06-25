package dev.claude.repository.security;

import dev.claude.domain.security.BlacklistTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistTokenEntity, Long> {
    public Optional<BlacklistTokenEntity> findByToken(String token);
}
