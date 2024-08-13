package kr.gyk.adobby.unsolved_backend.dto.problem;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemContentDTO {
    private String time;
    private String memory;
    private String submit;
    private String correct;
    private String accepted;
    private String correctRate;

    private String title;
    private String content;
    private String input;
    private String output;
}
