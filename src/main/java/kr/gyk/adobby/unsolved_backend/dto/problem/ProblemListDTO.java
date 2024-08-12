package kr.gyk.adobby.unsolved_backend.dto.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ProblemListDTO {
    private Long count;
    private List<ProblemDetailDTO> problemList;
}
