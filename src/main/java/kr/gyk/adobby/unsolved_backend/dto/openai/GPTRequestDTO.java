package kr.gyk.adobby.unsolved_backend.dto.openai;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTRequestDTO {
    private String model;
    private List<GPTRequestMessageDTO> messages;
}
