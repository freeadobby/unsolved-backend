package kr.gyk.adobby.unsolved_backend.repository.problem;

import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemReplyRepository extends JpaRepository<ProblemReply, Long> {
}
