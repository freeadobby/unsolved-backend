package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsElementDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.exception.RequestParamsNotValidException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemsListService {
    private final ProblemRepository problemRepository;
    private final ProblemTagRepository problemTagRepository;

    public ProblemsDTO getProblemList (Long fromId, Long toId) throws Exception {
        if (fromId > toId) throw new RequestParamsNotValidException("Params not Valid");
        List<Problem> problemList = problemRepository.findAllByOrderByIdAsc();
        if (toId == -1) toId = problemList.getLast().getId();
        List<ProblemsElementDTO> problemListElement = new ArrayList<>();
        for (Problem problem : problemList) if (problem.getId() >= fromId && problem.getId() <= toId)
                problemListElement.add(ProblemsElementDTO.builder()
                        .id(problem.getId())
                        .title(problem.getTitle())
                        .level(problem.getLevelCustom())
                        .build()
                );
        return ProblemsDTO.builder()
                .problemList(problemListElement)
                .count((long)problemListElement.size())
                .build();
    }

    public ProblemsDTO getProblemsAll () throws Exception {
        List<Problem> problemList = problemRepository.findAllByOrderByIdAsc();
        List<ProblemsElementDTO> problemListElement = new ArrayList<>();
        for (Problem problem : problemList) {
            ProblemsElementDTO problemsElementDTO = ProblemsElementDTO.builder()
                    .id(problem.getId())
                    .title(problem.getTitle())
                    .level(problem.getLevelCustom())
                    .build();
            List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
            for (var problemTag : problem.getTag()) problemTagDTOList.add(
                    ProblemTagDTO.builder()
                            .id(problemTag.getId())
                            .idBOJ(problemTag.getIdBOJ())
                            .idSolvedAC(problemTag.getIdSolvedAC())
                            .name(problemTag.getName())
                            .build());
            problemsElementDTO.setTags(problemTagDTOList);
            problemListElement.add(problemsElementDTO);
        }
        return ProblemsDTO.builder()
                .problemList(problemListElement)
                .count((long)problemListElement.size())
                .build();
    }

    public ProblemsDTO getProblemsTier (Integer tier) throws Exception {
        List<Problem> problemList = problemRepository.findAllByLevelCustom(tier);
        List<ProblemsElementDTO> problemListElement = new ArrayList<>();
        for (Problem problem : problemList) {
            ProblemsElementDTO problemsElementDTO = ProblemsElementDTO.builder()
                    .id(problem.getId())
                    .title(problem.getTitle())
                    .level(problem.getLevelCustom())
                    .build();
            List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
            for (var problemTag : problem.getTag()) problemTagDTOList.add(
                    ProblemTagDTO.builder()
                            .id(problemTag.getId())
                            .idBOJ(problemTag.getIdBOJ())
                            .idSolvedAC(problemTag.getIdSolvedAC())
                            .name(problemTag.getName())
                            .build());
            problemsElementDTO.setTags(problemTagDTOList);
            problemListElement.add(problemsElementDTO);
        }
        return ProblemsDTO.builder()
                .problemList(problemListElement)
                .count((long)problemListElement.size())
                .build();
    }

    public ProblemsDTO getProblemsTag (Integer tag) throws Exception {
        List<Problem> problemList = problemTagRepository.findById(tag).orElseThrow(() -> new DataNotFoundException("Entity not found")).getProblem();
        List<ProblemsElementDTO> problemListElement = new ArrayList<>();
        for (Problem problem : problemList) {
            ProblemsElementDTO problemsElementDTO = ProblemsElementDTO.builder()
                    .id(problem.getId())
                    .title(problem.getTitle())
                    .level(problem.getLevelCustom())
                    .build();
            List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
            for (var problemTag : problem.getTag()) problemTagDTOList.add(
                    ProblemTagDTO.builder()
                            .id(problemTag.getId())
                            .idBOJ(problemTag.getIdBOJ())
                            .idSolvedAC(problemTag.getIdSolvedAC())
                            .name(problemTag.getName())
                            .build());
            problemsElementDTO.setTags(problemTagDTOList);
            problemListElement.add(problemsElementDTO);
        }
        return ProblemsDTO.builder()
                .problemList(problemListElement)
                .count((long)problemListElement.size())
                .build();
    }

}
