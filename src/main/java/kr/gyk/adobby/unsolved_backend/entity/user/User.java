package kr.gyk.adobby.unsolved_backend.entity.user;

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

    @Column(unique = true, length = 128, nullable = false)
    private String email;

    @Column(length = 32, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @JoinColumn(name = "baekjoon")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BaekjoonID baekjoonID;

    @JoinColumn(name = "user_extend")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserExtend userExtend;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    private String refreshToken;

    public void setUserExtend(UserExtend userExtend) {
        this.userExtend = userExtend;
        userExtend.setId(this.getId());
        userExtend.setUser(this);
    }

    public void setBaekjoonID(BaekjoonID baekjoonID) {
        this.baekjoonID = baekjoonID;
        baekjoonID.setUser(this);
    }

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }
}
