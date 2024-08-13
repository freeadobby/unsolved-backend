package kr.gyk.adobby.unsolved_backend.dto.openai;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResponseChoicesDTO {
    private Integer index;
    private GPTResponseMessageDTO message;
}