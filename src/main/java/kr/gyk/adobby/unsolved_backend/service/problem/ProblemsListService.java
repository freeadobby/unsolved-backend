package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemListDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemListElementDTO;
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

    public ProblemListDTO getProblemList(Long fromId, Long toId) throws Exception {
        if (fromId > toId) throw new RequestParamsNotValidException("Params not Valid");
        List<Problem> problemList = problemRepository.findAllByOrderByIdAsc();
        if (toId == -1) toId = problemList.getLast().getId();
        List<ProblemListElementDTO> problemListElement = new ArrayList<>();
        for (Problem problem : problemList) if (problem.getId() >= fromId && problem.getId() <= toId)
                problemListElement.add(ProblemListElementDTO.builder()
                        .id(problem.getId())
                        .title(problem.getTitle())
                        .level(problem.getLevelCustom())
                        .tags(problem.getTag())
                        .build()
                );
        return ProblemListDTO.builder()
                .problemList(problemListElement)
                .count((long)problemListElement.size())
                .build();
    }
}
