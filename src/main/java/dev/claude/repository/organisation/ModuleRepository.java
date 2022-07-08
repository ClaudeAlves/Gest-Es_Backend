package dev.claude.repository.organisation;

import dev.claude.domain.organisation.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    boolean existsByName(String name);
    boolean existsByAbbreviation(String abbreviation);
    boolean existsByNumber(int number);
}
