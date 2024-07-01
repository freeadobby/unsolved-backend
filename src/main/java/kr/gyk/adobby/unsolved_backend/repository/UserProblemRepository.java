package kr.gyk.adobby.unsolved_backend.repository;

import kr.gyk.adobby.unsolved_backend.entity.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {
}
