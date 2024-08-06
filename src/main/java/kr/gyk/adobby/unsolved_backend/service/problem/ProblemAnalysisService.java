package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.UserAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.user.UserRepository;
import kr.gyk.adobby.unsolved_backend.service.user.UserAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemAnalysisService {
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final UserAnalysisService userAnalysisService;

    public ProblemAnalysisDTO getProblemAnalysis (Long problemId, String userEmail) throws Exception {
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new DataNotFoundException("Problem not found"));
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new DataNotFoundException("User not found"));
        UserAnalysisDTO userAnalysisDTO = userAnalysisService.getUserAnalysis(userEmail);

        ArrayList<ProblemTagDTO> problemTags = new ArrayList<>();
        for (var tag : problem.getTag()) problemTags.add(new ProblemTagDTO(tag));
        ProblemAnalysisDTO problemAnalysisDTO = ProblemAnalysisDTO.builder()
                .user(SignRequestDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .baekjoon(user.getBaekjoonID().getUsername())
                        .build())
                .problem(ProblemDetailDTO.builder()
                        .id(problem.getId())
                        .title(problem.getTitle())
                        .acceptedUserCount(problem.getAcceptedUserCount())
                        .isSprout(problem.getIsSprout())
                        .levelCustom(problem.getLevelCustom())
                        .levelSolvedAC(problem.getLevelSolvedAC())
                        .tags(problemTags)
                        .build())
                .scoreProblem(problem.getLevelCustom())
                .userScore(userAnalysisDTO.getRatingTier().intValue())
                .build();
        problemAnalysisDTO.setTotalScore((problem.getLevelCustom() - userAnalysisDTO.getRatingTier().intValue()) / 100 * 35); // (ProblemScore - UserScore) / 100 * 35

        // TODO :: Opinion

        return problemAnalysisDTO;
    }

}
