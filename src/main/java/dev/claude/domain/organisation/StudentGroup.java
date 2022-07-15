package dev.claude.domain.organisation;

import dev.claude.domain.user.AppUser;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;

/**
 * this class may be named "class" in the endpoints
 * it represents a class/ a group of students
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StudentGroup")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStudentGroup;

    private String name;

    private String comment;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<AppUser> students;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Course> courses;
}
