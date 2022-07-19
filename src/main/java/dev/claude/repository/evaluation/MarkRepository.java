package dev.claude.repository.evaluation;

import dev.claude.domain.evalutation.Mark;
import dev.claude.domain.evalutation.Test;
import dev.claude.domain.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByTest_Period_Course_IdCourse(Long idCourse);

    Optional<Mark> findByTestAndAndStudent(Test test, AppUser student);
}
