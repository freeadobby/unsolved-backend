package kr.gyk.adobby.unsolved_backend.entity.user;

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
public class UserExtend {
    @Id
    private Long id;

    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private User user;

    @JoinColumn(name = "accepted_problem")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserProblem> acceptedProblem = new ArrayList<>();

    // TODO:: Insert User Analysis Data

    

}
