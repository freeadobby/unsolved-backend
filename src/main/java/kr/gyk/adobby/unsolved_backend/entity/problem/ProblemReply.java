package kr.gyk.adobby.unsolved_backend.entity.problem;

import jakarta.persistence.*;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @JoinColumn(name = "problem")
    @ManyToOne
    private Problem problem;

    @ManyToOne
    private User author;
}
