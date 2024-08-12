package kr.gyk.adobby.unsolved_backend.dto.problem;

import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
public class ProblemPatchDTO {
    private Long id;
    private Optional<String> title;
    private Optional<Long> acceptedUserCount;
    private Optional<Boolean> isSprout;
    private Optional<Integer> levelCustom;
    private Optional<Integer> levelSolvedAC;
    private Optional<List<ProblemTagDTO>> tags;

    // TODO :: Add more Information of Problem
}
