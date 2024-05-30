package kr.gyk.adobby.unsolved_backend.repository;

import kr.gyk.adobby.unsolved_backend.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
