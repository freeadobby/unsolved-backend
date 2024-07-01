package kr.gyk.adobby.unsolved_backend.service;

import kr.gyk.adobby.unsolved_backend.dto.ProblemDetailDTO;
import kr.gyk.adobby.unsolved_backend.repository.ProblemRepository;
import kr.gyk.adobby.unsolved_backend.repository.ProblemTagRepository;
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
