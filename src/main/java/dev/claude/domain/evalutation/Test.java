package dev.claude.domain.evalutation;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.organisation.Course;
import dev.claude.domain.organisation.StudentGroup;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private String text;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Period period;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudentGroup studentGroup;
}
