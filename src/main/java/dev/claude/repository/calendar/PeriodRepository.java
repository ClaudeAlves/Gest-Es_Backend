package dev.claude.repository.calendar;

import dev.claude.domain.calendar.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
        public List<Period> findAllByCourse_StudentGroups_Students_IdUser(long idStudent);
        public List<Period> findAllByCourse_Teacher_IdUser(long isTeacher);
        public List<Period> findAllByCourse_StudentGroups_IdStudentGroup(long idStudentGroup);

}
