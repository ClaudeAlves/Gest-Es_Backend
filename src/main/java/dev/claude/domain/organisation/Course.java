package dev.claude.domain.organisation;

import dev.claude.domain.user.AppUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCourse;

    private String name;

    private LocalDate start;

    private LocalDate end;

    /**
     * This is used to create the periods of a course over it's duration.
     * I take for bases periods of 1hour from 8 in the morning to 5:30 in the evening.
     * The first period of the week monday 8 to 9 in the morning is set to be period 1.
     * The last period of the week is going to be friday at 5:30 in the evening, it is set to be period 40.
     * It gives us a total of 8 periods per day and 40 periods in a week.
     */
    @ElementCollection
    private Collection<Integer> periodsOfTheWeek;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<StudentGroup> studentGroups;

    @ManyToOne
    private AppUser teacher;
}
