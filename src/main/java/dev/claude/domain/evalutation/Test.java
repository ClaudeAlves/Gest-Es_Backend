package dev.claude.domain.evalutation;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.user.AppUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTest;

    private int number;

    private Date start;

    private Date end;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
