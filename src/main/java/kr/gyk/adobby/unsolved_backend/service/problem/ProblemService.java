package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.dto.problem.*;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemTagRepository problemTagRepository;

    public ProblemListDTO getProblemDetailSingle(Long problemId) throws Exception {
        ProblemDetailDTO dto = getProblemDetailDTO(problemId);
        if (dto == null) throw new DataNotFoundException("Cannot find Data using ID");
        List<ProblemDetailDTO> problemList = Collections.singletonList(dto);
        return ProblemListDTO.builder()
                .count(1L)
                .problemList(problemList)
                .build();
    }

    public ProblemListDTO getProblemDetailByRange(Long fromId, Optional<Long> toId) throws Exception {
        Long toId_mod = 0L;
        if (toId.isEmpty() || toId.get() == -1) toId_mod = problemRepository.findAllByOrderByIdAsc().getFirst().getId();
        List<ProblemDetailDTO> problemList = new ArrayList<>();
        for (Long i = fromId; i <= toId_mod; i++) {
            ProblemDetailDTO dto = getProblemDetailDTO(i);
            if (dto == null) continue;
            problemList.add(dto);
        }
        return ProblemListDTO.builder()
                .count((long)problemList.size())
                .problemList(problemList)
                .build();
    }

    public ProblemListDTO getProblemDetailByList(ProblemRequestListDTO request) throws Exception {
        List<ProblemDetailDTO> problemList = new ArrayList<>();
        for (Long id : request.getProblemIds()) {
            ProblemDetailDTO dto = getProblemDetailDTO(id);
            if (dto == null) continue;
            problemList.add(dto);
        }
        return ProblemListDTO.builder()
                .count((long)problemList.size())
                .problemList(problemList)
                .build();
    }

    private ProblemDetailDTO getProblemDetailDTO(Long id) {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem == null) return null;
        List<ProblemTagDTO> problemTagDTOList = new ArrayList<>();
        for (var tag : problem.getTag()) problemTagDTOList.add(new ProblemTagDTO(tag));
        return ProblemDetailDTO.builder()
                .id(id)
                .title(problem.getTitle())
                .acceptedUserCount(problem.getAcceptedUserCount())
                .isSprout(problem.getIsSprout())
                .levelCustom(problem.getLevelCustom())
                .levelSolvedAC(problem.getLevelSolvedAC())
                .tags(problemTagDTOList)
                // TODO :: Add more Information of Problem
                .build();
    }

    public boolean createProblem(ProblemDetailDTO request) throws Exception {
        try {
            Problem problem = Problem.builder()
                    .title(request.getTitle())
                    .acceptedUserCount(request.getAcceptedUserCount())
                    .isSprout(request.getIsSprout())
                    .levelCustom(request.getLevelCustom())
                    .levelSolvedAC(request.getLevelSolvedAC())
                    .build();
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

    public boolean updateProblem(ProblemPatchDTO request) throws Exception {
        try {
            // TODO: 비필수 정보 처리
            Problem problem = problemRepository.findById(request.getId()).orElseThrow(() -> new DataNotFoundException("Problem not found"));
            if (request.getTitle().isPresent()) problem.setTitle(request.getTitle().get());
            if (request.getAcceptedUserCount().isPresent()) problem.setAcceptedUserCount(request.getAcceptedUserCount().get());
            if (request.getIsSprout().isPresent()) problem.setIsSprout(request.getIsSprout().get());
            if (request.getLevelCustom().isPresent()) problem.setLevelCustom(request.getLevelCustom().get());
            if (request.getLevelSolvedAC().isPresent()) problem.setLevelSolvedAC(request.getLevelSolvedAC().get());
            if (request.getTags().isPresent()) {
                List<ProblemTag> problemTags = new ArrayList<>();
                for (var tag : request.getTags().get()) problemTags.add(problemTagRepository.findById(tag.getId()).orElseThrow(() -> new DataNotFoundException("Cannot find Data using ID")));
                problem.setTag(problemTags);
            }
            problemRepository.save(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}