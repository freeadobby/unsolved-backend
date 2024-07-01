package kr.gyk.adobby.unsolved_backend.repository.user;

import kr.gyk.adobby.unsolved_backend.entity.user.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {
}
