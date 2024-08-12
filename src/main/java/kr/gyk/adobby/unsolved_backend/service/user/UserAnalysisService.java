package kr.gyk.adobby.unsolved_backend.service.user;

import kr.gyk.adobby.unsolved_backend.dto.crawling.UserStatsDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsElementDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.UserAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
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
                .tagScore_math(0L)
                .tagScore_NumberTheory(0L)
                .tagScore_Graph(0L)
                .tagScore_Greedy(0L)
                .tagScore_Sort(0L)
                .tagScore_Tree(0L)
                .tagScore_Search(0L)
                .tagScore_implementation(0L)
                .tagScore_ShortestPath(0L)
                .tagScore_TwoPointer(0L)
                .tagScore_dynamicPrograming(0L)
                .tagScore_dataStructure(0L)
                .build();

        long[] tagCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        List<ProblemsElementDTO> problemsElementDTOList = new ArrayList<>();
        for (var problemId : userStatsDTO.getProblemSolved()) {
            Problem problem = null;
            try {
                problem = problemRepository.findById(problemId).orElseThrow(() -> new DataNotFoundException("Problem not found"));
            } catch (Exception e) { continue; }
            List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
            for (var problemTag : problem.getTag()) {
                problemTagDTOList.add(ProblemTagDTO.builder()
                            .id(problemTag.getId())
                            .idBOJ(problemTag.getIdBOJ())
                            .idSolvedAC(problemTag.getIdSolvedAC())
                            .name(problemTag.getName())
                        .build());
                if (problemTag.getIdBOJ() == 124) { tagCount[0]++; userAnalysisDTO.setTagScore_math(userAnalysisDTO.getTagScore_math() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 102) { tagCount[1]++; userAnalysisDTO.setTagScore_implementation(userAnalysisDTO.getTagScore_implementation() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 175) { tagCount[2]++; userAnalysisDTO.setTagScore_dataStructure(userAnalysisDTO.getTagScore_dataStructure() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 95) { tagCount[3]++; userAnalysisDTO.setTagScore_NumberTheory(userAnalysisDTO.getTagScore_NumberTheory() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 25) { tagCount[4]++; userAnalysisDTO.setTagScore_dynamicPrograming(userAnalysisDTO.getTagScore_dynamicPrograming() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 33) { tagCount[5]++; userAnalysisDTO.setTagScore_Greedy(userAnalysisDTO.getTagScore_Greedy() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 11 || problemTag.getIdBOJ() == 12 || problemTag.getIdBOJ() == 126 || problemTag.getIdBOJ() == 127) { tagCount[6]++; userAnalysisDTO.setTagScore_Search(userAnalysisDTO.getTagScore_Search() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 7) { tagCount[7]++; userAnalysisDTO.setTagScore_Graph(userAnalysisDTO.getTagScore_Graph() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 120) { tagCount[8]++; userAnalysisDTO.setTagScore_Tree(userAnalysisDTO.getTagScore_Tree() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 80) { tagCount[9]++; userAnalysisDTO.setTagScore_TwoPointer(userAnalysisDTO.getTagScore_TwoPointer() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 215) { tagCount[10]++; userAnalysisDTO.setTagScore_ShortestPath(userAnalysisDTO.getTagScore_ShortestPath() + problem.getLevelCustom());
                } else if (problemTag.getIdBOJ() == 97) { tagCount[11]++; userAnalysisDTO.setTagScore_Sort(userAnalysisDTO.getTagScore_Sort() + problem.getLevelCustom()); }
            }
            problemsElementDTOList.add(ProblemsElementDTO.builder().id(problemId).title(problem.getTitle()).level(problem.getLevelCustom()).tags(problemTagDTOList).build());
        }
        userAnalysisDTO.setSolvedProblemList(ProblemsDTO.builder()
                        .problemList(problemsElementDTOList)
                        .count((long) problemsElementDTOList.size())
                .build());

        userAnalysisDTO.setSolvedTodayProblemList(ProblemsDTO.builder().problemList(new ArrayList<>()).count(0L).build()); // TODO :: 오늘의 학습 목록 dto 작성

        double userRatingScore = 0;// average(sum(상위 100문제 점수)) + 정답률(100%) / 10 + (20 * (1 - 0.995 ^ 해결한 문제 수))
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
        userRatingScore /= 100.0;
        userRatingScore += (double) userStatsDTO.getSCountRight() / (double) userStatsDTO.getSCountSubmit() * 10.0
                + 20 * (1 - Math.pow(0.995, userStatsDTO.getSProblemSolved()));
        userAnalysisDTO.setRatingScore((int) userRatingScore);

        userAnalysisDTO.setRatingTier(getTier(userRatingScore));

        userAnalysisDTO.setTagScore_math((long) ((double) userAnalysisDTO.getTagScore_math() / tagCount[0] * 2 + 30 * (1 - Math.pow(0.995, tagCount[0]))));
        userAnalysisDTO.setTagScore_implementation((long) ((double) userAnalysisDTO.getTagScore_implementation() / tagCount[1] * 2 + 30 * (1 - Math.pow(0.995, tagCount[1]))));
        userAnalysisDTO.setTagScore_dataStructure((long) ((double) userAnalysisDTO.getTagScore_dataStructure() / tagCount[2] * 2 + 30 * (1 - Math.pow(0.995, tagCount[2]))));
        userAnalysisDTO.setTagScore_NumberTheory((long) ((double) userAnalysisDTO.getTagScore_NumberTheory() / tagCount[3] * 2 + 30 * (1 - Math.pow(0.995, tagCount[3]))));
        userAnalysisDTO.setTagScore_dynamicPrograming((long) ((double) userAnalysisDTO.getTagScore_dynamicPrograming() / tagCount[4] * 2 + 30 * (1 - Math.pow(0.995, tagCount[4]))));
        userAnalysisDTO.setTagScore_Greedy((long) ((double) userAnalysisDTO.getTagScore_Greedy() / tagCount[5] * 2 + 30 * (1 - Math.pow(0.995, tagCount[5]))));
        userAnalysisDTO.setTagScore_Search((long) ((double) userAnalysisDTO.getTagScore_Search() / tagCount[6] * 2 + 30 * (1 - Math.pow(0.995, tagCount[6]))));
        userAnalysisDTO.setTagScore_Graph((long) ((double) userAnalysisDTO.getTagScore_Graph() / tagCount[7] * 2 + 30 * (1 - Math.pow(0.995, tagCount[7]))));
        userAnalysisDTO.setTagScore_Tree((long) ((double) userAnalysisDTO.getTagScore_Tree() / tagCount[8] * 2 + 30 * (1 - Math.pow(0.995, tagCount[8]))));
        userAnalysisDTO.setTagScore_TwoPointer((long) ((double) userAnalysisDTO.getTagScore_TwoPointer() / tagCount[9] * 2 + 30 * (1 - Math.pow(0.995, tagCount[9]))));
        userAnalysisDTO.setTagScore_ShortestPath((long) ((double) userAnalysisDTO.getTagScore_ShortestPath() / tagCount[10] * 2 + 30 * (1 - Math.pow(0.995, tagCount[10]))));
        userAnalysisDTO.setTagScore_Sort((long) ((double) userAnalysisDTO.getTagScore_Sort() / tagCount[11] * 2 + 30 * (1 - Math.pow(0.995, tagCount[11]))));

        int tagMax1 = 0, tagMax2 = 0, tagMax3 = 0;
        int tagMin1 = 0, tagMin2 = 0;
        for (int i = 1; i < 12; i++) {
            if (tagCount[i] >= tagCount[tagMax1]) {
                tagMax3 = tagMax2;
                tagMax2 = tagMax1;
                tagMax1 = i;
            }
            if (tagCount[i] <= tagCount[tagMin1]) {
                tagMin2 = tagMin1;
                tagMin1 = i;
            }
        }

        String[] tagKey = {"수학", "구현", "자료구조", "정수론", "DP", "그리디", "탐색", "그래프", "트리", "투 포인터", "최단 경로", "정렬"};

        userAnalysisDTO.setOpinion(tagKey[tagMax1] + ", " + tagKey[tagMax2] + "에 대한 이해도는 높고, " + tagKey[tagMin1] + ", " + tagKey[tagMin2] + " 등에 대한 공부가 필요합니다. 또한 " + tagKey[tagMax3] + "에 대한 심도 있는 공부를 추천합니다.");

        userAnalysisDTO.setNeedLearning(tagKey[tagMax3] + ", " + tagKey[tagMin1] + ", " + tagKey[tagMin2]);

        return userAnalysisDTO;
    }

    private static String getTier(double userRatingScore) {
        String tier = "Unrated";

        int userRating = (int) ((userRatingScore - 100) / 500);
        if (userRating == 0) tier = "Copper";
        else if (userRating == 1) tier = "Bronze";
        else if (userRating == 2) tier = "Silver";
        else if (userRating == 3) tier = "Gold";
        else if (userRating == 4) tier = "Platinum";
        else if (userRating == 5) tier = "Emerald";
        else if (userRating == 6) tier = "Diamond";

        userRating = (int) (userRatingScore - userRating * 500) / 100;
        if (userRating == 0) tier += " V";
        else if (userRating == 1) tier += " IV";
        else if (userRating == 2) tier += " III";
        else if (userRating == 3) tier += " II";
        else if (userRating == 4) tier += " I";

        return tier;
    }

    public ProblemsDTO getProblemsReview (String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new DataNotFoundException("User not found"));
        UserStatsDTO userStatsDTO = crawlingUserService.getUserStats(user.getBaekjoonID().getUsername());

        List<ProblemsElementDTO> problemsElementDTOList = new ArrayList<>();
        for (var problemId : userStatsDTO.getProblemSolved()) {
            Problem problem = null;
            try {
                problem = problemRepository.findById(problemId).orElseThrow(() -> new DataNotFoundException("Problem not found"));
            } catch (Exception e) { continue; }
            List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
            for (var problemTag : problem.getTag()) {
                problemTagDTOList.add(ProblemTagDTO.builder()
                        .id(problemTag.getId())
                        .idBOJ(problemTag.getIdBOJ())
                        .idSolvedAC(problemTag.getIdSolvedAC())
                        .name(problemTag.getName())
                        .build());
            }
            problemsElementDTOList.add(ProblemsElementDTO.builder().id(problemId).title(problem.getTitle()).level(problem.getLevelCustom()).tags(problemTagDTOList).build());
        }
        Collections.shuffle(problemsElementDTOList);

        List<ProblemsElementDTO> problemsElementDTOListNew = new ArrayList<>();
        for (int i = 0; i < 9 && i < problemsElementDTOList.size(); i++) problemsElementDTOListNew.add(problemsElementDTOList.get(i));

        return ProblemsDTO.builder()
                .problemList(problemsElementDTOListNew)
                .count((long) problemsElementDTOListNew.size())
                .build();
    }

    public ProblemsDTO getProblemsRecommend (String userEmail) throws Exception {
        UserAnalysisDTO userAnalysisDTO = getUserAnalysis(userEmail);
        int userTier = (int) (userAnalysisDTO.getRatingScore() / 100.0);
        if (userTier != 35) userTier += 1;

        List<Problem> problems = problemRepository.findAllByLevelCustom(userTier);
        Collections.shuffle(problems);

        List<ProblemsElementDTO> problemsElementDTOList = new ArrayList<>();
        for (int i = 0; i < 9 && i < problems.size(); i++) {
            Problem problem = problems.get(i);
            List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
            for (var problemTag : problem.getTag()) {
                problemTagDTOList.add(ProblemTagDTO.builder()
                        .id(problemTag.getId())
                        .idBOJ(problemTag.getIdBOJ())
                        .idSolvedAC(problemTag.getIdSolvedAC())
                        .name(problemTag.getName())
                        .build());
            }
            problemsElementDTOList.add(ProblemsElementDTO.builder().id(problem.getId()).title(problem.getTitle()).level(problem.getLevelCustom()).tags(problemTagDTOList).build());
        }
        return ProblemsDTO.builder()
                .problemList(problemsElementDTOList)
                .count((long) problemsElementDTOList.size())
                .build();
    }
}
