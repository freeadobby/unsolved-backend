package kr.gyk.adobby.unsolved_backend.repository.user;

import kr.gyk.adobby.unsolved_backend.entity.user.BaekjoonID;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaekjoonIDRepository extends JpaRepository<BaekjoonID, Long> {
    Optional<BaekjoonID> findByUser(User user);
}
