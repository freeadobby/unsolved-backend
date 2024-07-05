package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemListDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemPatchDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemRequestListDTO;
import kr.gyk.adobby.unsolved_backend.exception.RequestParamsNotValidException;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("")
    public ResponseEntity<ProblemListDTO> getProblem (@RequestParam Optional<Long> id, @RequestParam Optional<Long> fromId, @RequestParam Optional<Long> toId, @RequestBody Optional<ProblemRequestListDTO> problemList) throws Exception {
        if (id.isPresent()) return new ResponseEntity<>(problemService.getProblemDetailSingle(id.get()), HttpStatus.OK);
        if (problemList.isPresent()) return new ResponseEntity<>(problemService.getProblemDetailByList(problemList.get()), HttpStatus.OK);
        if (fromId.isPresent()) return new ResponseEntity<>(problemService.getProblemDetailByRange(fromId.get(), toId), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Boolean> updateProblem(@RequestBody ProblemPatchDTO problemPatchDTODTO) throws Exception {
        return new ResponseEntity<>(problemService.updateProblem(problemPatchDTODTO), HttpStatus.OK);
    }
}
