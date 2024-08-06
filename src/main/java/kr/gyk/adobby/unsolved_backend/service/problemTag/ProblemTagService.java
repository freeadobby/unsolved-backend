package kr.gyk.adobby.unsolved_backend.service.problemTag;

import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemTagService {
    private final ProblemTagRepository problemTagRepository;

    public ProblemTagDTO getProblemTagById(ProblemTagDTO request) throws Exception {
        Optional<ProblemTag> tag = problemTagRepository.findById(request.getId());
        if (tag.isEmpty()) tag = problemTagRepository.findByIdBOJ(request.getIdBOJ());
        if (tag.isEmpty()) tag = problemTagRepository.findByIdSolvedAC(request.getIdSolvedAC());
        if (tag.isEmpty()) throw new DataNotFoundException("Tag Not Found");
        return new ProblemTagDTO(tag.get());
    }

    public Boolean createProblemTag(ProblemTagDTO request) {
        try {
            ProblemTag problemTag = ProblemTag.builder()
                    .idBOJ(request.getIdBOJ())
                    .idSolvedAC(request.getIdSolvedAC())
                    .name(request.getName())
                    .build();
            problemTagRepository.save(problemTag);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean deleteProblemTag(Integer id) {
        try {
            problemTagRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
