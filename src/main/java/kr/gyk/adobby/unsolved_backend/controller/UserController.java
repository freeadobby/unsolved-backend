package kr.gyk.adobby.unsolved_backend.controller;

import kr.gyk.adobby.unsolved_backend.dto.LogoutRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.TokenDTO;
import kr.gyk.adobby.unsolved_backend.repository.UserRepository;
import kr.gyk.adobby.unsolved_backend.service.UserService;
import kr.gyk.adobby.unsolved_backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<SignResponseDTO> login(@RequestBody SignRequestDTO request) throws Exception {
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<SignResponseDTO> getUser(@RequestParam String email) throws Exception {
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody LogoutRequestDTO request) throws Exception {
        return new ResponseEntity<>(userService.logout(request), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody SignRequestDTO request) throws Exception {
        return new ResponseEntity<>(userService.register(request), HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO token) throws Exception {
        return new ResponseEntity<>(tokenService.refreshAccessToken(token), HttpStatus.OK);
    }

}
