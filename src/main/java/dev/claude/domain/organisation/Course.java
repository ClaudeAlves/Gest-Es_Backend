package dev.claude.domain.organisation;

import dev.claude.domain.user.AppUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

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

    private Date start;

    private Date end;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<AppUser> Students;

    @ManyToOne
    private AppUser Teacher;
}
