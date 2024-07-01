package kr.gyk.adobby.unsolved_backend.service.user;

import kr.gyk.adobby.unsolved_backend.dto.user.TokenDTO;
import kr.gyk.adobby.unsolved_backend.entity.user.AccessTokenBlackList;
import kr.gyk.adobby.unsolved_backend.entity.user.RefreshToken;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import kr.gyk.adobby.unsolved_backend.jwt.JwtProvider;
import kr.gyk.adobby.unsolved_backend.repository.user.AccessTokenBlackListRepository;
import kr.gyk.adobby.unsolved_backend.repository.user.RefreshTokenRepository;
import kr.gyk.adobby.unsolved_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenBlackListRepository accessTokenBlackListRepository;

    public String createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.builder()
                .id(user.getId())
                .refreshToken(UUID.randomUUID().toString())
                .expiration(60 * 60)
                .build()
        );
        return refreshToken.getRefreshToken();
    }

    public RefreshToken validateRefreshToken(User user, String refreshToken) throws Exception {
        RefreshToken token = refreshTokenRepository.findById(user.getId()).orElseThrow(() -> new Exception("Session is expired"));
        if (token.getRefreshToken() == null) return null;
        if (token.getExpiration() > 10) {
            token.setExpiration(60 * 60);
            refreshTokenRepository.save(token);
        }
        if (!token.getRefreshToken().equals(refreshToken)) return null;
        return token;
    }

    public void deleteRefreshToken(User user) throws Exception{
        if (refreshTokenRepository.findById(user.getId()).isEmpty()) throw new Exception("Session expired");
        refreshTokenRepository.deleteById(user.getId());
    }

    public void addAccessTokenBlackList(User user, String accessToken) throws Exception {
        accessTokenBlackListRepository.save(AccessTokenBlackList.builder().id(user.getId()).accessToken(accessToken).expiration(1000L * 60 * 10).build());
    }

    public TokenDTO refreshAccessToken(TokenDTO token) throws Exception {
        String accountEmail = jwtProvider.getEmail(token.getAccessToken());
        User user = userRepository.findByEmail(accountEmail).orElseThrow(() -> new BadCredentialsException("Invalid Account Information"));
        RefreshToken refreshToken = validateRefreshToken(user, token.getRefreshToken());
        if (refreshToken == null) throw new Exception("Login First");
        this.addAccessTokenBlackList(user, token.getAccessToken());
        return TokenDTO.builder()
                .accessToken(jwtProvider.createToken(accountEmail, user.getRoles()))
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
