package kr.gyk.adobby.unsolved_backend.service.problemTag;

import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagCreateDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.dto.problemTag.ProblemTagListDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemTagService {
    private final ProblemTagRepository problemTagRepository;

    public ProblemTagDTO getProblemTagById(ProblemTagDTO request) throws Exception {
        Optional<ProblemTag> tag = Optional.empty();
        try {
            tag = problemTagRepository.findById(request.getId());
        } catch (Exception e) { tag = Optional.empty(); }

        if (tag.isEmpty())
            try {
                tag = problemTagRepository.findByIdBOJ(request.getIdBOJ());
            } catch (Exception e) { tag = Optional.empty(); }

        if (tag.isEmpty())
            try {
                tag = problemTagRepository.findByIdSolvedAC(request.getIdSolvedAC());
            } catch (Exception e) { tag = Optional.empty(); }

        if (tag.isEmpty()) throw new DataNotFoundException("Tag Not Found");

        return new ProblemTagDTO(tag.get());
    }

    public ProblemTagDTO getProblemTagByIdBOJ (Integer bojTag) throws Exception {
        ProblemTag tag = problemTagRepository.findByIdBOJ(bojTag).orElseThrow(() -> new DataNotFoundException("Entity not found"));
        return new ProblemTagDTO(tag);
    }

    public ProblemTagDTO getProblemTagById (Integer tag) throws Exception {
        ProblemTag problemTag = problemTagRepository.findById(tag).orElseThrow(() -> new DataNotFoundException("Entity not found"));
        return new ProblemTagDTO(problemTag);
    }

    public ProblemTagListDTO getProblemTagAll () throws Exception {
        List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
        List<ProblemTag> problemTagList = problemTagRepository.findAllByOrderByIdAsc();
        for (var problemTag : problemTagList)
            problemTagDTOList.add(ProblemTagDTO.builder()
                            .id(problemTag.getId())
                            .idBOJ(problemTag.getIdBOJ())
                            .idSolvedAC(problemTag.getIdSolvedAC())
                            .name(problemTag.getName())
                    .build());
        return ProblemTagListDTO.builder()
                .count(problemTagDTOList.size())
                .problemTag(problemTagDTOList)
                .build();
    }

    public Boolean createProblemTag (ProblemTagCreateDTO request) {
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
