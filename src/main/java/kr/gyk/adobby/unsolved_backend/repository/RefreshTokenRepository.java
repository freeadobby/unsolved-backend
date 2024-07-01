package kr.gyk.adobby.unsolved_backend.repository;

import kr.gyk.adobby.unsolved_backend.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
