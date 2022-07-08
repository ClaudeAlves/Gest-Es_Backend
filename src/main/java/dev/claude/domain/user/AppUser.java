package dev.claude.domain.user;

import dev.claude.domain.organisation.StudentGroup;
import dev.claude.domain.organisation.Subject;
import dev.claude.domain.organisation.Module;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@With
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "User")
public class AppUser {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @Singular
    private Set<Role> roles;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Subject> subjects;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Module> modules;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<StudentGroup> studentGroups;

}
