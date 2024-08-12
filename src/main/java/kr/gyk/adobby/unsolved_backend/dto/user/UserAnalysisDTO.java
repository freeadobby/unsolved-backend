package kr.gyk.adobby.unsolved_backend.dto.user;

import kr.gyk.adobby.unsolved_backend.dto.crawling.UserStatsDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnalysisDTO {
    private SignRequestDTO user;

    private UserStatsDTO userStats;

    private ProblemsDTO solvedProblemList;
    private ProblemsDTO solvedTodayProblemList;

    private Integer ratingScore;
    private String ratingTier;

    private Long tagScore_math; // 수학
    private Long tagScore_implementation; // 구현
    private Long tagScore_dataStructure; // 자료구조
    private Long tagScore_NumberTheory; // 정수론
    private Long tagScore_dynamicPrograming; // DP
    private Long tagScore_Greedy; // 그리디
    private Long tagScore_Search; // 탐색
    private Long tagScore_Graph; // 그래프
    private Long tagScore_Tree; // 트리
    private Long tagScore_TwoPointer; // 투 포인터
    private Long tagScore_ShortestPath; // 최단 경로
    private Long tagScore_Sort; // 정렬

    private String opinion;
    private String needLearning;
}