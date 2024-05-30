package kr.gyk.adobby.unsolved_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 127, nullable = false)
    private String email;

    @Column(length = 31, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @JoinColumn(name = "baekjoon")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Baekjoon baekjoon;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    private String refreshToken;

    public void setBaekjoon(Baekjoon baekjoon) {
        this.baekjoon = baekjoon;
        baekjoon.setUser(this);
    }

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }
}
