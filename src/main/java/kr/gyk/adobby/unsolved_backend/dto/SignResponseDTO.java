package kr.gyk.adobby.unsolved_backend.dto;

import kr.gyk.adobby.unsolved_backend.entity.Token;
import kr.gyk.adobby.unsolved_backend.entity.User;
import kr.gyk.adobby.unsolved_backend.entity.Authority;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponseDTO {
    private Long id;
    private String email;
    private String username;
    private String baekjoon;
    private List<Authority> roles = new ArrayList<>();
    private TokenDTO token;

    public SignResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.baekjoon = user.getBaekjoon().getUsername();
        this.roles = user.getRoles();
    }
}
