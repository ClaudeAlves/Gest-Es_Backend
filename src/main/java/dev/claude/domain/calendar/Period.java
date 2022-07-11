package dev.claude.domain.calendar;

import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.organisation.Subject;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.organisation.Module;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Period")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPeriod;

    private String tag;

    private String text;

    private LocalDateTime start;

    private LocalDateTime  end;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;


}
