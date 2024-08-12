package kr.gyk.adobby.unsolved_backend.repository.user;

import kr.gyk.adobby.unsolved_backend.entity.user.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
