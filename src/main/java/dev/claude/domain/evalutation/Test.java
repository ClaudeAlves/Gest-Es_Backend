package dev.claude.domain.evalutation;

import dev.claude.domain.organisation.Course;
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

    private LocalDateTime start;

    private LocalDateTime  end;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
