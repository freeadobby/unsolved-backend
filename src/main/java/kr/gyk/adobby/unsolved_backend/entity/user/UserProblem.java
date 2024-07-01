package kr.gyk.adobby.unsolved_backend.entity.user;

import jakarta.persistence.*;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private User user;

    @JoinColumn(name = "problem")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private Problem problem;

    @Column(nullable = false)
    private LocalDateTime acceptedDate;

    @Column(nullable = false)
    private Integer attempt;
}
