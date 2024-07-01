package kr.gyk.adobby.unsolved_backend.service.user;

import kr.gyk.adobby.unsolved_backend.dto.user.LogoutRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.TokenDTO;
import kr.gyk.adobby.unsolved_backend.entity.user.Authority;
import kr.gyk.adobby.unsolved_backend.entity.user.BaekjoonID;
import kr.gyk.adobby.unsolved_backend.entity.user.User;
import kr.gyk.adobby.unsolved_backend.entity.user.UserExtend;
import kr.gyk.adobby.unsolved_backend.jwt.JwtProvider;
import kr.gyk.adobby.unsolved_backend.repository.user.UserRepository;
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
            User user = this.getUser(request.getEmail());
            tokenService.deleteRefreshToken(user);
            tokenService.addAccessTokenBlackList(user, request.getAccessToken());
        } catch (Exception e) {
            throw new Exception("Bad Request");
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

    /*
    public boolean delete(LogoutRequestDTO request) throws Exception {
        // TODO:: Account Delete Logic
        try {
            User user = this.getUser(request.getEmail());
            userRepository.delete(user);

        }
        return false;
    }
     */

    public SignResponseDTO getUserResponse(String email) throws Exception {
        return new SignResponseDTO(this.getUser(email));
    }

    public User getUser(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
    }
}
