package dev.claude.repository.evaluation;

import dev.claude.domain.evalutation.Test;
import dev.claude.domain.organisation.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByCourse_StudentGroups_Students_IdUser(Long idStudent);
}
