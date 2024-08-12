package kr.gyk.adobby.unsolved_backend.controller.problem;

import kr.gyk.adobby.unsolved_backend.dto.problemReply.ProblemReplyDTO;
import kr.gyk.adobby.unsolved_backend.service.problemReply.ProblemReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem/reply")
public class ProblemReplyController {
    private final ProblemReplyService problemReplyService;

    @PostMapping("")
    public ResponseEntity<Boolean> createReply(@RequestBody ProblemReplyDTO request) throws Exception {
        return new ResponseEntity<>(problemReplyService.createReply(request), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ProblemReplyDTO> getReply(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(problemReplyService.getReply(id), HttpStatus.OK);
    }

}
