package dev.claude.repository.organisation;

import dev.claude.domain.organisation.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Class, Long> {
}
