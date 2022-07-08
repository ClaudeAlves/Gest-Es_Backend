package dev.claude.domain.organisation;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idModule;

    private String name;

    private String abbreviation;

    private int number;

    private int period_number;

    private String description;

    @OneToMany
    private Collection<Subject> subjects;
}
