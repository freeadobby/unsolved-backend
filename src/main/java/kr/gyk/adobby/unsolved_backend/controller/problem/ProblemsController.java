package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemListDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.UserAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemService;
import kr.gyk.adobby.unsolved_backend.service.problem.ProblemsListService;
import kr.gyk.adobby.unsolved_backend.service.user.UserAnalysisService;
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
    private final UserAnalysisService userAnalysisService;

    @GetMapping("")
    public ResponseEntity<ProblemsDTO> getProblems(@RequestParam Long fromId, @RequestParam Long toId) throws Exception {
        return new ResponseEntity<>(problemsListService.getProblemList(fromId, toId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ProblemsDTO> getProblemsAll () throws Exception {
        return new ResponseEntity<>(problemsListService.getProblemsAll(), HttpStatus.OK);
    }

    @GetMapping("/tier")
    public ResponseEntity<ProblemsDTO> getProblemsTier (@RequestParam Integer tier) throws Exception {
        return new ResponseEntity<>(problemsListService.getProblemsTier(tier), HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<ProblemsDTO> getProblemsTag (@RequestParam Integer tag) throws Exception {
        return new ResponseEntity<>(problemsListService.getProblemsTag(tag), HttpStatus.OK);
    }

    @GetMapping("/review")
    public ResponseEntity<ProblemsDTO> getProblemsReview (@RequestParam String user) throws Exception {
        return new ResponseEntity<>(userAnalysisService.getProblemsReview(user), HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ProblemsDTO> getProblemsRecommend (@RequestParam String user) throws Exception {
        return new ResponseEntity<>(userAnalysisService.getProblemsRecommend(user), HttpStatus.OK);
    }

}