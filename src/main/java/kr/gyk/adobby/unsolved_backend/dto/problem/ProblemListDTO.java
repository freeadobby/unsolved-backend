package kr.gyk.adobby.unsolved_backend.dto.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ProblemListDTO {
    private Long count;
    private List<ProblemListElementDTO> problemList = new ArrayList<>();
}
