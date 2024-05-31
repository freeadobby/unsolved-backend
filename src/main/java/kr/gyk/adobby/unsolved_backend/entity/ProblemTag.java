package kr.gyk.adobby.unsolved_backend.entity;

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
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer idBOJ;

    @Column(nullable = true, unique = true)
    private String idSolvedAC;

    @Column(nullable = true, length = 255)
    private String name;

    @JoinColumn(name = "tag")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Problem> problem = new ArrayList<>();
}