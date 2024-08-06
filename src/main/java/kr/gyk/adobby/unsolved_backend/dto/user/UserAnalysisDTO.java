package kr.gyk.adobby.unsolved_backend.dto.user;

import kr.gyk.adobby.unsolved_backend.dto.crawling.UserStatsDTO;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnalysisDTO {
    private SignRequestDTO user;

    private UserStatsDTO userStats;

    private Double ratingScore;
    private Short ratingTier;

    private Double tagScore_math; // 수학
    private Double tagScore_implementation; // 구현
    private Double tagScore_dataStructure; // 자료구조
    private Double tagScore_NumberTheory; // 정수론
    private Double tagScore_dynamicPrograming; // DP
    private Double tagScore_Greedy; // 그리디
    private Double tagScore_Search; // 탐색
    private Double tagScore_Graph; // 그래프
    private Double tagScore_Tree; // 트리
    private Double tagScore_TwoPointer; // 투 포인터
    private Double tagScore_ShortestPath; // 최단 경로
    private Double tagScore_Sort; // 정렬

    private String opinion;
}