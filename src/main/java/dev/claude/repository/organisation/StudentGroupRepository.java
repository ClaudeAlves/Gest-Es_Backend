package dev.claude.repository.organisation;

import dev.claude.domain.organisation.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    boolean existsByName(String name);

    List<StudentGroup> findAllByCourses_Teacher_IdUser(Long id);

    List<StudentGroup> findAllByCourses_IdCourse(Long id);

    List<StudentGroup> findAllByStudents_IdUser(Long id);
}
