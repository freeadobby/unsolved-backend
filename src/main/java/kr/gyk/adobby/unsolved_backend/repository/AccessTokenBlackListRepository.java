package kr.gyk.adobby.unsolved_backend.repository;

import kr.gyk.adobby.unsolved_backend.entity.AccessTokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<AccessTokenBlackList, Long> {
}
