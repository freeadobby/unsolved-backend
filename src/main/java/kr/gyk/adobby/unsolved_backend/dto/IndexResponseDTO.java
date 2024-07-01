package kr.gyk.adobby.unsolved_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndexResponseDTO {
    private String heading;
    private String team;
    private String project;
    private String version;
}