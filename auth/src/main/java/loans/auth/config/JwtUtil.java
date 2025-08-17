package loans.auth.config;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import loans.auth.dto.UserDTO;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    private SecretKey key;

    @PostConstruct
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public String generate(UserDTO user, String tokenType) {
        Map<String, String> claims = Map.of(
                "role", user.getRole().name(),
                "email", user.getEmail());

        // Parse the expiration string to long
        long expirationMillis = "ACCESS".equalsIgnoreCase(tokenType)
                ? Long.parseLong(expiration) // For ACCESS tokens
                : Long.parseLong(expiration) * 5; // For REFRESH tokens (5x longer)

        final Date now = new Date();
        final Date exp = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    private boolean isExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}
