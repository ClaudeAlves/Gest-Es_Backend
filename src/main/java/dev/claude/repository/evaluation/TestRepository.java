package dev.claude.repository.evaluation;

import dev.claude.domain.evalutation.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByPeriod_Course_StudentGroups_Students_IdUser(Long idStudent);

    List<Test> findAllByPeriod_Course_IdCourse(Long idCourse);
    Integer countAllByPeriod_Course_IdCourse(Long idCourse);
    List<Test> findAllByPeriod_Course_StudentGroups_IdStudentGroup(Long id);

    List<Test> findAllByPeriod_Course_Teacher_IdUser(Long idTeacher);
}
