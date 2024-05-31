package kr.gyk.adobby.unsolved_backend.repository;

import kr.gyk.adobby.unsolved_backend.entity.BaekjoonID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonIDRepository extends JpaRepository<BaekjoonID, Long> {
}
