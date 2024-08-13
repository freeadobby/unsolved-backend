package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.config.WebClientConfig;
import kr.gyk.adobby.unsolved_backend.dto.openai.GPTRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.openai.GPTRequestMessageDTO;
import kr.gyk.adobby.unsolved_backend.dto.openai.GPTResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.openai.GPTResponseMessageDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemContentDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.UserAnalysisDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.user.UserRepository;
import kr.gyk.adobby.unsolved_backend.service.crawling.JSoupService;
import kr.gyk.adobby.unsolved_backend.service.user.UserAnalysisService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemAnalysisService {
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final UserAnalysisService userAnalysisService;
    private final WebClient webClient;

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
                .scoreProblem(problem.getLevelCustom() * 100)
                .scoreUser(userAnalysisDTO.getRatingScore())
                .scoreAnalysis(problem.getLevelCustom() * 100 + 20)
                .build();
        problemAnalysisDTO.setTotalScore((int) ((problemAnalysisDTO.getScoreAnalysis() - userAnalysisDTO.getRatingScore()) / 3.5)); // (ProblemScore - UserScore) / 3500 * 100

        ProblemContentDTO problemContentDTO = this.baekjoonProblem(problemId.intValue());
        List<GPTRequestMessageDTO> requestMessageDTOS = new ArrayList<>();
        requestMessageDTOS.add(GPTRequestMessageDTO.builder()
                .role("user")
                .content("문제 7줄로 짧게 요약해줘." + problemContentDTO.getContent() + "\n" + problemContentDTO.getInput() + "\n" + problemContentDTO.getOutput())
                .build());
        GPTResponseDTO gptResponseDTO = webClient.post()
                .bodyValue(GPTRequestDTO.builder()
                        .model("gpt-4o")
                        .messages(requestMessageDTOS)
                        .build())
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .block();
        assert gptResponseDTO != null;
        problemAnalysisDTO.setOpinion(gptResponseDTO.getChoices().getFirst().getMessage().getContent());

        return problemAnalysisDTO;
    }

    public ProblemContentDTO baekjoonProblem (Integer problemId) throws Exception{
        Connection connection = JSoupService.getConnection("https://www.acmicpc.net/problem/" + problemId);
        return ProblemContentDTO.builder()
                .time(JSoupService.getElements(connection, "#problem-info > tbody > tr > td:nth-child(1)").text())
                .memory(JSoupService.getElements(connection, "#problem-info > tbody > tr > td:nth-child(2)").text())
                .submit(JSoupService.getElements(connection, "#problem-info > tbody > tr > td:nth-child(3)").text())
                .correct(JSoupService.getElements(connection, "#problem-info > tbody > tr > td:nth-child(4)").text())
                .accepted(JSoupService.getElements(connection, "#problem-info > tbody > tr > td:nth-child(5)").text())
                .correctRate(JSoupService.getElements(connection, "#problem-info > tbody > tr > td:nth-child(6)").text())
                .title(JSoupService.getElements(connection, "#problem_title").text())
                .content(JSoupService.getElements(connection, "#problem_description").text())
                .input(JSoupService.getElements(connection, "#problem_input").text())
                .output(JSoupService.getElements(connection, "#problem_output").text())
                .build();
    }

}
