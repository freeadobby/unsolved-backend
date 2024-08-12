package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagCreateDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagListDTO;
import kr.gyk.adobby.unsolved_backend.service.problemTag.ProblemTagService;
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

    @GetMapping("/boj")
    public ResponseEntity<ProblemTagDTO> getProblemTagByBoj(@RequestParam Integer tag) throws Exception {
        return new ResponseEntity<>(problemTagService.getProblemTagByIdBOJ(tag), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<ProblemTagDTO> getProblemTagById(@RequestParam Integer tag) throws Exception {
        return new ResponseEntity<>(problemTagService.getProblemTagById(tag), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ProblemTagListDTO> getProblemTagAll () throws Exception {
        return new ResponseEntity<>(problemTagService.getProblemTagAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createProblemTag(@RequestBody ProblemTagCreateDTO problemTagCreateDTO) throws Exception {
        return new ResponseEntity<>(problemTagService.createProblemTag(problemTagCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteProblemTag(@RequestParam Integer id) throws Exception {
        return new ResponseEntity<>(problemTagService.deleteProblemTag(id), HttpStatus.OK);
    }

}
