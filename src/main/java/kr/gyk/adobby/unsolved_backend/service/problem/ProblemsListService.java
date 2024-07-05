package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemsElementDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.exception.RequestParamsNotValidException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
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

    public ProblemsDTO getProblemList(Long fromId, Long toId) throws Exception {
        if (fromId > toId) throw new RequestParamsNotValidException("Params not Valid");
        List<Problem> problemList = problemRepository.findAllByOrderByIdAsc();
        if (toId == -1) toId = problemList.getLast().getId();
        List<ProblemsElementDTO> problemListElement = new ArrayList<>();
        for (Problem problem : problemList) if (problem.getId() >= fromId && problem.getId() <= toId)
                problemListElement.add(ProblemsElementDTO.builder()
                        .id(problem.getId())
                        .title(problem.getTitle())
                        .level(problem.getLevelCustom())
                        .tags(problem.getTag())
                        .build()
                );
        return ProblemsDTO.builder()
                .problemList(problemListElement)
                .count((long)problemListElement.size())
                .build();
    }
}
