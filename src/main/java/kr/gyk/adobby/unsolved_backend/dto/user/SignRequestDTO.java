package kr.gyk.adobby.unsolved_backend.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignRequestDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String baekjoon;
}
