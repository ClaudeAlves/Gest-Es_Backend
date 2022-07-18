package dev.claude.repository.organisation;

import dev.claude.domain.organisation.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);
    List<Course> findAllByStudentGroups_IdStudentGroup(Long idStudentGroup);
    List<Course> findAllByTeacher_IdUser(Long idTeacher);
    List<Course> findAllByStudentGroups_Students_IdUser(Long idStudent);
}
