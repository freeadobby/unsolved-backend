package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.dto.problem.ProblemTagDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
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
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemTagRepository problemTagRepository;

    public ProblemDetailDTO getProblemDetail(Long problemId) throws Exception {
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new DataNotFoundException("Cannot find Data using ID"));
        ProblemDetailDTO problemDetailDTO = ProblemDetailDTO.builder()
                .id(problemId)
                .title(problem.getTitle())
                .acceptedUserCount(problem.getAcceptedUserCount())
                .isSprout(problem.getIsSprout())
                .levelCustom(problem.getLevelCustom())
                .levelSolvedAC(problem.getLevelSolvedAC())
                // TODO :: Add more Information of Problem
                .build();
        List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
        for (var tag : problem.getTag()) problemTagDTOList.add(new ProblemTagDTO(tag));
        problemDetailDTO.setTags(problemTagDTOList);
        return problemDetailDTO;
    }

    public boolean createProblem(ProblemDetailDTO request) throws Exception {
        try {
            Problem problem = Problem.builder()
                    .title(request.getTitle())
                    .acceptedUserCount(request.getAcceptedUserCount())
                    .isSprout(request.getIsSprout())
                    .levelCustom(request.getLevelCustom()).build();
            List<ProblemTag> problemTags = new ArrayList<>();
            for (var tag : request.getTags()) problemTags.add(problemTagRepository.findById(tag.getId()).orElseThrow(() -> new DataNotFoundException("Cannot find Data using ID")));
            problem.setTag(problemTags);
            problemRepository.save(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteProblem(Long problemId) throws Exception {
        try {
            problemRepository.deleteById(problemId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateProblem(ProblemDetailDTO request) throws Exception {
        try {
            Problem problem = problemRepository.findById(request.getId()).orElseThrow(() -> new DataNotFoundException("Problem not found"));
            problem.setTitle(request.getTitle());
            problem.setAcceptedUserCount(request.getAcceptedUserCount());
            problem.setIsSprout(request.getIsSprout());
            problem.setLevelCustom(request.getLevelCustom());
            problem.setLevelSolvedAC(request.getLevelSolvedAC());
            List<ProblemTag> problemTags = new ArrayList<>();
            for (var tag : request.getTags()) problemTags.add(problemTagRepository.findById(tag.getId()).orElseThrow(() -> new DataNotFoundException("Cannot find Data using ID")));
            problem.setTag(problemTags);
            problemRepository.save(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}