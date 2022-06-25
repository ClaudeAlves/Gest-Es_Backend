package dev.claude.domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder(toBuilder = true)
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private EnumRole name;

}
