package kr.gyk.adobby.unsolved_backend.service;

import kr.gyk.adobby.unsolved_backend.dto.LogoutRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.TokenDTO;
import kr.gyk.adobby.unsolved_backend.entity.*;
import kr.gyk.adobby.unsolved_backend.jwt.JwtProvider;
import kr.gyk.adobby.unsolved_backend.repository.AccessTokenBlackListRepository;
import kr.gyk.adobby.unsolved_backend.repository.RefreshTokenRepository;
import kr.gyk.adobby.unsolved_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenBlackListRepository accessTokenBlackListRepository;

    public SignResponseDTO login(SignRequestDTO request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new BadCredentialsException("Invalid Password");
        return SignResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .baekjoon(user.getBaekjoon().getUsername())
                .roles(user.getRoles())
                .token(TokenDTO.builder()
                        .accessToken(jwtProvider.createToken(user.getEmail(), user.getRoles()))
                        .refreshToken(createRefreshToken(user))
                        .build()
                )
                .build();
    }

    public boolean logout(LogoutRequestDTO request) throws Exception {
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
            if (refreshTokenRepository.findById(user.getId()).isEmpty()) throw new Exception("Session expired");
            refreshTokenRepository.deleteById(user.getId());
            accessTokenBlackListRepository.save(AccessTokenBlackList.builder().id(user.getId()).accessToken(request.getAccessToken()).expiration(1000L * 60 * 10).build());
        } catch (Exception e) {
            throw new Exception("Bad Reqeust");
        }
        return true;
    }

    public boolean register(SignRequestDTO request) throws Exception {
        try {
            User user = User.builder()
                    .id(request.getId())
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            user.setBaekjoon(Baekjoon.builder().username(request.getBaekjoon()).build());
            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Bad Request");
        }
        return true;
    }

    public SignResponseDTO getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("Cannot find account"));
        return new SignResponseDTO(user);
    }

    public String createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.builder()
                .id(user.getId())
                .refreshToken(UUID.randomUUID().toString())
                .expiration(60 * 60)
                .build()
        );
        return refreshToken.getRefreshToken();
    }

    public RefreshToken valideRefreshToken(User user, String refreshToken) throws Exception {
        RefreshToken token = refreshTokenRepository.findById(user.getId()).orElseThrow(() -> new Exception("Session is expired"));
        if (token.getRefreshToken() == null) return null;
        if (token.getExpiration() > 10) {
            token.setExpiration(60 * 60);
            refreshTokenRepository.save(token);
        }
        if (!token.getRefreshToken().equals(refreshToken)) return null;
        return token;
    }

    public TokenDTO refreshAccessToken(TokenDTO token) throws Exception {
        String accountEmail = jwtProvider.getEmail(token.getAccessToken());
        User user = userRepository.findByEmail(accountEmail).orElseThrow(() -> new BadCredentialsException("Invalid Account Information"));
        RefreshToken refreshToken = valideRefreshToken(user, token.getRefreshToken());
        if (refreshToken == null) throw new Exception("Login First");
        return TokenDTO.builder()
                .accessToken(jwtProvider.createToken(accountEmail, user.getRoles()))
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
