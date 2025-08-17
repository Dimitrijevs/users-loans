package loans.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import loans.auth.client.UserClient;
import loans.auth.config.JwtUtil;
import loans.auth.dto.LoginRequest;
import loans.auth.dto.TokenResponse;
import loans.auth.dto.UserDTO;
import loans.auth.exception.InvalidCredentialsException;
import loans.auth.exception.UserAlreadyExistsException;
import loans.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // public TokenResponse authenticateAndGenerateToken(LoginRequest loginRequest)
    // {

    // if (isValidUser(loginRequest.getUsername(), loginRequest.getPassword())) {
    // String token = createTokenFromUsername(loginRequest.getUsername());
    // long expiresIn = expiration;
    // return new TokenResponse(token, expiresIn);
    // } else {
    // throw new RuntimeException("Invalid credentials");
    // }
    // }

    public TokenResponse login(LoginRequest request) {
        try {
            // This will throw UserNotFoundException if user doesn't exist
            UserDTO user = userClient.findByEmailWithPassword(request.getEmail());

            System.out.println(user.toString());

            // Add debugging logs
            System.out.println("Raw password from request: " + request.getPassword());
            System.out.println("Encoded password from DB: " + user.getPassword());
            System.out.println(
                    "Password match result: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));

            System.out.println(user.toString());

            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                System.out.println("test123");
                throw new InvalidCredentialsException("Invalid email or password");
            }

            // Generate tokens if credentials are valid
            String accessToken = jwtUtil.generate(user, "ACCESS");
            // String refreshToken = jwtUtil.generate(user.getId(), user.getRole(),
            // "REFRESH");

            long expiresInSeconds = expiration / 1000;

            return TokenResponse.builder()
                    .token(accessToken)
                    .expiresIn(String.valueOf(expiresInSeconds)) // seconds
                    .build();

        } catch (UserNotFoundException e) {
            // User doesn't exist - don't reveal this, use generic message
            System.out.println("Invalid email or password test");
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    public boolean isExpired(String token) {
        return jwtUtil.isExpired(token);
    }
}
