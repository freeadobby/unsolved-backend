package kr.gyk.adobby.unsolved_backend.controller;

import kr.gyk.adobby.unsolved_backend.dto.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private ProblemService problemService;

    /*
    @GetMapping("")
    public ResponseEntity<ProblemDetailDTO> getProblem(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(problemService.getProblemDetail(id), HttpStatus.OK);
    }
     */
}
