package kr.gyk.adobby.unsolved_backend.dto.problem;

import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemAnalysisDTO {
    private ProblemDetailDTO problem;
    private SignRequestDTO user;

    private Integer scoreAnalysis;
    private Integer scoreProblem;
    private Integer scoreUser;

    private String opinion;
    private Integer totalScore;
}
