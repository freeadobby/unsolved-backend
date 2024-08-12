package kr.gyk.adobby.unsolved_backend.repository.user;

import kr.gyk.adobby.unsolved_backend.entity.user.AccessTokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<AccessTokenBlackList, Long> {
}
