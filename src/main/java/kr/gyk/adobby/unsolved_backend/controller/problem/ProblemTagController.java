package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class ProblemTagController {
    private final ProblemTagService problemTagService;

    @GetMapping("")
    public ResponseEntity<ProblemTagDTO> getProblemTag(@RequestBody ProblemTagDTO problemTagDTO) throws Exception {
        return new ResponseEntity<>(problemTagService.getProblemTagById(problemTagDTO), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createProblemTag(@RequestBody ProblemTagDTO problemTagDTO) throws Exception {
        return new ResponseEntity<>(problemTagService.createProblemTag(problemTagDTO), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteProblemTag(@RequestParam Integer id) throws Exception {
        return new ResponseEntity<>(problemTagService.deleteProblemTag(id), HttpStatus.OK);
    }
}
