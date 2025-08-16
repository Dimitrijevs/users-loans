package loans.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import loans.auth.dto.LoginRequest;
import loans.auth.dto.TokenResponse;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public TokenResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
        
        if (isValidUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            String token = createTokenFromUsername(loginRequest.getUsername());
            long expiresIn = expiration;
            return new TokenResponse(token, expiresIn);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // private final RestTemplate restTemplate;
    // private final JwtUtil jwtUtil;

    // public AuthResponse register(AuthRequest request) {
    //     //do validation if user exists in DB
    //     request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
    //     UserVO registeredUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);

    //     String accessToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "ACCESS");
    //     String refreshToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");

    //     return new AuthResponse(accessToken, refreshToken);
    // }

    
    // Generates a JWT token for a given username
    private String createTokenFromUsername(String username) {

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    private boolean isValidUser(String username, String password) {
        // TODO: Replace with your actual user validation logic
        // This could be:
        // - Database lookup
        // - LDAP authentication
        // - External service call
        // - etc.
        
        // Simple hardcoded validation for demo (REMOVE IN PRODUCTION)
        return "admin".equals(username) && "password".equals(password);
    }
}
