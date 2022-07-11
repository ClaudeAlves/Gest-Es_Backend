package dev.claude.domain.evalutation;

import dev.claude.domain.user.AppUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMark;

    private Double value;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Test test;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser student;
}
