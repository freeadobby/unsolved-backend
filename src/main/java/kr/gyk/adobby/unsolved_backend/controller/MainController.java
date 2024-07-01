package kr.gyk.adobby.unsolved_backend.controller;

import kr.gyk.adobby.unsolved_backend.dto.IndexResponseDTO;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("")
    public ResponseEntity<IndexResponseDTO> index() throws Exception {
        IndexResponseDTO response = IndexResponseDTO.builder()
                .heading("Soongsil University School of Software Software Contest Exhibiton")
                .team("Adobby")
                .project("Redis")
                .version("v0.1")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
