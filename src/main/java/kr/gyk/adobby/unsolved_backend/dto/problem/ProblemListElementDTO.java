package kr.gyk.adobby.unsolved_backend.dto.problem;


import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ProblemListElementDTO {
    private Long id;
    private String title;
    private Integer level;
    private List<ProblemTag> tags = new ArrayList<>();
}
