package kr.gyk.adobby.unsolved_backend.repository.problem;

import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTagRepository extends JpaRepository<ProblemTag, Integer> {
}
