package kr.gyk.adobby.unsolved_backend.dto.problemTag;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProblemTagCreateDTO {
    private Integer idBOJ;
    private String idSolvedAC;
    private String name;
}
