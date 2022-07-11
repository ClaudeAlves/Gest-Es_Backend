package dev.claude.domain.organisation;

import dev.claude.domain.calendar.Period;
import dev.claude.domain.user.AppUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.swing.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Absence")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAbsence;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser giver;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser student;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Period period;
}
