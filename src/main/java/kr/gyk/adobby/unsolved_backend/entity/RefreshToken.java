package kr.gyk.adobby.unsolved_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@RedisHash("refreshToken")
public class RefreshToken {
    @Id
    @JsonIgnore
    private Long id;

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Integer expiration;
}
