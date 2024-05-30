package kr.gyk.adobby.unsolved_backend.service;

import kr.gyk.adobby.unsolved_backend.dto.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.entity.Authority;
import kr.gyk.adobby.unsolved_backend.entity.Baekjoon;
import kr.gyk.adobby.unsolved_backend.entity.User;
import kr.gyk.adobby.unsolved_backend.jwt.JwtProvider;
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
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignResponseDTO login(SignRequestDTO request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new BadCredentialsException("Invalid Password");
        return SignResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .baekjoon(user.getBaekjoon().getUsername())
                .roles(user.getRoles())
                .token(jwtProvider.createToken(user.getEmail(), user.getRoles()))
                .build();
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
}
