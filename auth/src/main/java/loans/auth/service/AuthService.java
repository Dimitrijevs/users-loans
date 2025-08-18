package loans.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import loans.auth.client.UserClient;
import loans.auth.config.JwtUtil;
import loans.auth.dto.LoginRequest;
import loans.auth.dto.TokenRequest;
import loans.auth.dto.TokenResponse;
import loans.auth.dto.UserDTO;
import loans.auth.exception.InvalidCredentialsException;
import loans.auth.exception.InvalidTokenException;
import loans.auth.exception.TokenExpiredException;
import loans.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public TokenResponse login(LoginRequest request) {
        try {
            // This will throw UserNotFoundException if user doesn't exist
            UserDTO user = userClient.findByEmailWithPassword(request.getEmail());

            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                System.out.println("test123");
                throw new InvalidCredentialsException("Invalid email or password");
            }

            // Generate tokens if credentials are valid
            String accessToken = jwtUtil.generate(user, "ACCESS");

            String refreshToken = jwtUtil.generate(user, "REFRESH");

            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (UserNotFoundException e) {

            // User doesn't exist - don't reveal this, use generic message
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    public boolean isExpired(TokenRequest request) {

        String token = request.getToken();

        return jwtUtil.isExpired(token);
    }

    public TokenResponse refreshToken(TokenRequest request) {

        String refreshToken = request.getToken();

        if(jwtUtil.isExpired(refreshToken)) {
            throw new TokenExpiredException("Token is expired, please log in again.");
        }

        Claims claims = jwtUtil.getClaims(refreshToken);

        if (!"REFRESH".equals(claims.get("token_type", String.class))) {
            throw new InvalidTokenException("Invalid token type. Only refresh tokens can be used for refreshing.");
        }

        try {
            // This will throw UserNotFoundException if user doesn't exist
            UserDTO user = userClient.findByEmailWithPassword(claims.get("sub", String.class));

            // Generate tokens if credentials are valid
            String accessToken = jwtUtil.generate(user, "ACCESS");

            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (UserNotFoundException e) {

            // User doesn't exist - don't reveal this, use generic message
            throw new InvalidCredentialsException("Invalid email.");
        }
    }
}
