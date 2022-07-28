package dev.claude.domain.evalutation;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.organisation.StudentGroup;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

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

    private double weighting;

    private String text;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Period period;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<StudentGroup> studentGroups;
}
