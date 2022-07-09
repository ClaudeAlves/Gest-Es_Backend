package dev.claude.repository.calendar;

import dev.claude.domain.calendar.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
