package kr.gyk.adobby.unsolved_backend.entity.user;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("accessTokenBlackList")
public class AccessTokenBlackList {
    @Id
    private Long id;

    private String accessToken;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expiration;
}
