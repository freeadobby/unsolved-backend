package kr.gyk.adobby.unsolved_backend.service.user;

import kr.gyk.adobby.unsolved_backend.dto.crawling.UserStatsDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.UserAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.user.UserRepository;
import kr.gyk.adobby.unsolved_backend.service.crawling.CrawlingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAnalysisService {
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final CrawlingUserService crawlingUserService;

    public UserAnalysisDTO getUserAnalysis (String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
        UserStatsDTO userStatsDTO = crawlingUserService.getUserStats(user.getBaekjoonID().getUsername());
        UserAnalysisDTO userAnalysisDTO = UserAnalysisDTO.builder()
                .user(SignRequestDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .baekjoon(user.getBaekjoonID().getUsername())
                        .build()
                )
                .userStats(userStatsDTO)
                .build();

        double userRatingScore = // average(sum(상위 100문제 점수)) + 정답률(100%) / 10 + (20 * (1 - 0.995 ^ 해결한 문제 수))
                (double) userStatsDTO.getSCountRight() / (double) userStatsDTO.getSCountSubmit() * 10.0
                + 20 * (1 - Math.pow(0.995, userStatsDTO.getSProblemSolved())) ;
        HashMap<Long, Integer> solvedProblemScore = new HashMap<>();
        for (var i : userStatsDTO.getProblemSolved())
            try {
                solvedProblemScore.put(i, problemRepository.findById(i).get().getLevelCustom());
            } catch (NoSuchElementException e) {
                continue;
            }
        Comparator<Map.Entry<Long, Integer>> comparator = new Comparator<>() {
            @Override
            public int compare (Map.Entry<Long, Integer> e1, Map.Entry<Long, Integer> e2) {
                return e1.getValue().compareTo(e2.getValue());
            }
        };
        for (int i = 0; i < 100 && i < userStatsDTO.getProblemSolved().size(); i++) {
            Map.Entry<Long, Integer> entry = Collections.max(solvedProblemScore.entrySet(), comparator);
            userRatingScore += (double) entry.getValue() * 100.0;
            solvedProblemScore.remove(entry.getKey(), entry.getValue());
        }
        userAnalysisDTO.setRatingScore(userRatingScore);

        // TODO :: 태그 점수

        // TODO :: 종합 의견

        return userAnalysisDTO;
    }
}
