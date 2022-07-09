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
@Entity(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSubject;

    private String name;

    private String abbreviation;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Module module;
}
