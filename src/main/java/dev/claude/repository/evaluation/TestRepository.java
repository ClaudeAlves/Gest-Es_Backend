package dev.claude.repository.evaluation;

import dev.claude.domain.evalutation.Test;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByPeriod_Course_StudentGroups_Students_IdUser(Long idStudent);

    Integer countAllByPeriod_Course_IdCourseAndStudentGroup_IdStudentGroup(Long idCourse, Long idStudentGroup);
    List<Test> findAllByPeriod_Course_StudentGroups_IdStudentGroup(Long id);

    List<Test> findAllByPeriod_Course_Teacher_IdUser(Long idTeacher);
}
