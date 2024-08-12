package kr.gyk.adobby.unsolved_backend.repository.problem;

import kr.gyk.adobby.unsolved_backend.entity.problem.Problem;
import kr.gyk.adobby.unsolved_backend.entity.problem.ProblemTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemTagRepository extends JpaRepository<ProblemTag, Integer> {
    Optional<ProblemTag> findByIdBOJ(Integer idBOJ);
    Optional<ProblemTag> findByIdSolvedAC(String idSolvedAC);
    List<ProblemTag> findAllByOrderByIdAsc();
}
