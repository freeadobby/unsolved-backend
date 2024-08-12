package kr.gyk.adobby.unsolved_backend.dto.problemReply;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemReplyDTO {
    private Long id;
    private Long problem;
    private String content;
    private String author;
    private LocalDateTime createDate;
}
