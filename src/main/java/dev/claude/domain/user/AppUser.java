package dev.claude.domain.user;

import lombok.*;

import javax.persistence.*;
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

}
