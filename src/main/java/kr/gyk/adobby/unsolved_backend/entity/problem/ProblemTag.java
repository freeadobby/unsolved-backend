package kr.gyk.adobby.unsolved_backend.entity.problem;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer idBOJ;

    @Column(nullable = true, unique = true)
    private String idSolvedAC;

    @Column(nullable = true, length = 255)
    private String name;

    @ManyToMany
    private List<Problem> problem = new ArrayList<>();
}