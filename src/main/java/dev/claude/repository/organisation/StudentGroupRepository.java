package dev.claude.repository.organisation;

import dev.claude.domain.organisation.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    boolean existsByName(String name);
}
