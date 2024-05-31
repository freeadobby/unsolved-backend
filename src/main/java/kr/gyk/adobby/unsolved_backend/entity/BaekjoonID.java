package kr.gyk.adobby.unsolved_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaekjoonID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
