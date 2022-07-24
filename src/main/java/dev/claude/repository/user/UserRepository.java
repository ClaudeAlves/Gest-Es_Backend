package dev.claude.repository.user;

import dev.claude.domain.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<AppUser> findAllByStudentGroups_Tests_IdTest(Long idTest);
    List<AppUser> findAllByStudentGroups_Courses_IdCourse(Long idCourse);
    List<AppUser> findAllByStudentGroups_Courses_Subject_Module_IdModule(Long idModule);
    List<AppUser> findAllByStudentGroups_IdStudentGroup(Long idStudentGroup);
}
