package kr.gyk.adobby.unsolved_backend.controller;

import kr.gyk.adobby.unsolved_backend.dto.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.TokenDTO;
import kr.gyk.adobby.unsolved_backend.repository.UserRepository;
import kr.gyk.adobby.unsolved_backend.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignController {
    private final UserRepository userRepository;
    private final SignService signService;

    @PostMapping("")
    public ResponseEntity<SignResponseDTO> login(@RequestBody SignRequestDTO request) throws Exception {
        return new ResponseEntity<>(signService.login(request), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<SignResponseDTO> getUser(@RequestParam String email) throws Exception {
        return new ResponseEntity<>(signService.getUser(email), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody SignRequestDTO request) throws Exception {
        return new ResponseEntity<>(signService.register(request), HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO token) throws Exception {
        return new ResponseEntity<>(signService.refreshAccessToken(token), HttpStatus.OK);
    }

}
