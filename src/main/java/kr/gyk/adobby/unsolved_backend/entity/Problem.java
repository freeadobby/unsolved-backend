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
public class Problem {
    @Id
    private Long id;

    @Column(nullable = false, length = 1023)
    private String title;

    @Column(nullable = false)
    private Long acceptedUserCount;

    @Column(nullable = false)
    private Boolean isSprout;

    @Column(nullable = false)
    private Integer levelSolvedAC;

    @JoinColumn(name = "problem")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProblemTag> roles = new ArrayList<>();
}
