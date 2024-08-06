package kr.gyk.adobby.unsolved_backend.dto.problemTag;

import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProblemTagDTO {
    private Integer id;
    private Integer idBOJ;
    private String idSolvedAC;
    private String name;

    public ProblemTagDTO(ProblemTag problemTag) {
        this.id = problemTag.getId();
        this.idBOJ = problemTag.getIdBOJ();
        this.idSolvedAC = problemTag.getIdSolvedAC();
        this.name = problemTag.getName();
    }
}
