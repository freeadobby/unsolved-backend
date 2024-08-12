package kr.gyk.adobby.unsolved_backend.dto.problem;

import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProblemDetailDTO {
    private Long id;
    private String title;
    private Long acceptedUserCount;
    private Boolean isSprout;
    private Integer levelCustom;
    private Integer levelSolvedAC;
    private List<ProblemTagDTO> tags;
}
