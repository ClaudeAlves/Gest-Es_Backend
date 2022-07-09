package dev.claude.domain.calendar;

import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.organisation.Subject;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.organisation.Module;
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
@Entity(name = "Period")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPeriod;

    private Date start;

    private Date end;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Module module;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser teacher;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<AppUser> students;


}
