package kr.gyk.adobby.unsolved_backend.repository.problem;

import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
