package loans.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import loans.auth.dto.LoginRequest;
import loans.auth.dto.TokenRequest;
import loans.auth.dto.TokenResponse;
import loans.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello from unsecure endpoint");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest registerRequest) {
        return authService.login(registerRequest);
    }

    @PostMapping("/is-expired")
    @ResponseStatus(HttpStatus.OK)
    public boolean isExpired(@RequestBody TokenRequest request) {

        return authService.isExpired(request);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse refreshToken(@RequestBody TokenRequest request) {
        
        return authService.refreshToken(request);
    }
}
