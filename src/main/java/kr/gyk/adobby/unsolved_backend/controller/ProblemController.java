package kr.gyk.adobby.unsolved_backend.controller;

import kr.gyk.adobby.unsolved_backend.service.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
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
