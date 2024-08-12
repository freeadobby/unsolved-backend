package kr.gyk.adobby.unsolved_backend.repository.problem;

import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByOrderByIdAsc();
    List<Problem> findAllByLevelCustom(Integer levelCustom);
}
