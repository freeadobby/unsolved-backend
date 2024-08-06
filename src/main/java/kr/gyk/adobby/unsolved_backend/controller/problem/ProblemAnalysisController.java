package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemAnalysisService;
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
public class ProblemAnalysisController {
    private final ProblemAnalysisService problemAnalysisService;

    @GetMapping("/analysis")
    public ResponseEntity<ProblemAnalysisDTO> getProblemAnalysis(@RequestParam Long problemId, @RequestParam String userEmail) throws Exception {
        return new ResponseEntity<>(problemAnalysisService.getProblemAnalysis(problemId, userEmail), HttpStatus.OK);
    }
}
