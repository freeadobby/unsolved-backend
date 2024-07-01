package kr.gyk.adobby.unsolved_backend.service.problem;

import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.problem.ProblemTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {
    private ProblemRepository problemRepository;
    private ProblemTagRepository problemTagRepository;

    /*
    public ProblemDetailDTO getProblemDetail(Long problemId) throws Exception{

    }*/
}
