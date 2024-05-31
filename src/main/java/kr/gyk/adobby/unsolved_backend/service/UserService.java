package kr.gyk.adobby.unsolved_backend.service;

import kr.gyk.adobby.unsolved_backend.dto.LogoutRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.TokenDTO;
import kr.gyk.adobby.unsolved_backend.entity.*;
import kr.gyk.adobby.unsolved_backend.jwt.JwtProvider;
import kr.gyk.adobby.unsolved_backend.repository.AccessTokenBlackListRepository;
import kr.gyk.adobby.unsolved_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AccessTokenBlackListRepository accessTokenBlackListRepository;
    private final TokenService tokenService;

    public SignResponseDTO login(SignRequestDTO request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new BadCredentialsException("Invalid Password");
        return SignResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .baekjoonID(user.getBaekjoonID().getUsername())
                .roles(user.getRoles())
                .token(TokenDTO.builder()
                        .accessToken(jwtProvider.createToken(user.getEmail(), user.getRoles()))
                        .refreshToken(tokenService.createRefreshToken(user))
                        .build()
                )
                .build();
    }

    public boolean logout(LogoutRequestDTO request) throws Exception {
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
            tokenService.deleteRefreshToken(user);
            tokenService.addAccessTokenBlackList(user, request.getAccessToken());
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
            user.setUserExtend(UserExtend.builder().id(user.getId()).build());
            user.setBaekjoonID(BaekjoonID.builder().username(request.getBaekjoon()).build());
            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Bad Request");
        }
        return true;
    }

    public boolean delete(LogoutRequestDTO request) throws Exception {
        // TODO:: Account Delete Logic
        return false;
    }

    public SignResponseDTO getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("Cannot find account"));
        return new SignResponseDTO(user);
    }
}
