package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsDTO;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemService;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemsListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemsController {
    private final ProblemService problemService;
    private final ProblemsListService problemsListService;

    @GetMapping("")
    public ResponseEntity<ProblemsDTO> getProblems(@RequestParam Long fromId, @RequestParam Long toId) throws Exception {
        return new ResponseEntity<>(problemsListService.getProblemList(fromId, toId), HttpStatus.OK);
    }
}

// TODO: 리스트 단위 기능 추가