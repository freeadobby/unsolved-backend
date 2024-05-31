package kr.gyk.adobby.unsolved_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemExtend {
    @Id
    private Long id;

    @JoinColumn(name = "problem")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Problem problem;

    //TODO:: Insert Problem Analysis Data
}
