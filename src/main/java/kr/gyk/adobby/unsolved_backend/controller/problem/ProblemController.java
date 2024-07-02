package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("")
    public ResponseEntity<ProblemDetailDTO> getProblem(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(problemService.getProblemDetail(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createProblem(@RequestBody ProblemDetailDTO problemDetailDTO) throws Exception {
        return new ResponseEntity<>(problemService.createProblem(problemDetailDTO), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteProblem(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(problemService.deleteProblem(id), HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<Boolean> updateProblem(@RequestBody ProblemDetailDTO problemDetailDTO) throws Exception {
        return new ResponseEntity<>(problemService.updateProblem(problemDetailDTO), HttpStatus.OK);
    }
}
