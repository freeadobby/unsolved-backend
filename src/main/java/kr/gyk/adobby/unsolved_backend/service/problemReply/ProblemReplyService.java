package kr.gyk.adobby.unsolved_backend.service.problemReply;

import kr.gyk.adobby.unsolved_backend.dto.problemReply.ProblemReplyDTO;
import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemReply;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import kr.gyk.adobby.unsolved_backend.exception.DataNotFoundException;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemReplyRepository;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemReplyService {
    private final ProblemReplyRepository problemReplyRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    public Boolean createReply (ProblemReplyDTO request) throws Exception{
        Problem problem = problemRepository.findById(request.getProblem()).orElseThrow(() -> new DataNotFoundException("Entity not found"));
        User user = userRepository.findByEmail(request.getAuthor()).orElseThrow(() -> new DataNotFoundException("User not found"));
        ProblemReply problemReply = ProblemReply.builder()
                .content(request.getContent())
                .createDate(LocalDateTime.now())
                .problem(problem)
                .author(user)
                .build();
        problemReplyRepository.save(problemReply);
        problem.getReplyList().add(problemReply);
        problemRepository.save(problem);
        return true;
    }

    public ProblemReplyDTO getReply (Long id) throws Exception{
        ProblemReply problemReply = problemReplyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Entity not found"));
        return ProblemReplyDTO.builder()
                .content(problemReply.getContent())
                .createDate(problemReply.getCreateDate())
                .problem(problemReply.getProblem().getId())
                .author(problemReply.getAuthor().getUsername())
                .build();
    }

}