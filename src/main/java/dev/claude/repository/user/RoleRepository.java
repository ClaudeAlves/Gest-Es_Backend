package dev.claude.repository.user;

import dev.claude.domain.user.EnumRole;
import dev.claude.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRole name);

    boolean existsByName(EnumRole name);
}
