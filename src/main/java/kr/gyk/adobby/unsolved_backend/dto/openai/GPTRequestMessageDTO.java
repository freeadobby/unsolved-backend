package kr.gyk.adobby.unsolved_backend.dto.openai;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTRequestMessageDTO {
    private String role;
    private String content;
}
