package loans.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    // private final AuthService authService;

    // @@PostMapping(value = "/register")
    // public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
    //     return ResponseEntity.ok(authService.register(request));
    // }   

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("AUTH service working");
    }
}
