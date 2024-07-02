package kr.gyk.adobby.unsolved_backend.controller.user;

import kr.gyk.adobby.unsolved_backend.dto.user.LogoutRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignRequestDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.SignResponseDTO;
import kr.gyk.adobby.unsolved_backend.dto.user.TokenDTO;
import kr.gyk.adobby.unsolved_backend.service.user.UserService;
import kr.gyk.adobby.unsolved_backend.service.user.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("")
    public ResponseEntity<SignResponseDTO> getUser(@RequestParam String email) throws Exception {
        return new ResponseEntity<>(userService.getUserResponse(email), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SignResponseDTO> login(@RequestBody SignRequestDTO request) throws Exception {
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
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
