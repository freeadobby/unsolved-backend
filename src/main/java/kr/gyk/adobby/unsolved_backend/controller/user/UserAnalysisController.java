package kr.gyk.adobby.unsolved_backend.controller.user;

import kr.gyk.adobby.unsolved_backend.dto.user.UserAnalysisDTO;
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
@RequestMapping("/user")
public class UserAnalysisController {
    private final UserAnalysisService userAnalysisService;

    @GetMapping("/analysis")
    public ResponseEntity<UserAnalysisDTO> getUserAnalysis (@RequestParam String email) throws Exception {
        return new ResponseEntity<>(userAnalysisService.getUserAnalysis(email), HttpStatus.OK);
    }

}
