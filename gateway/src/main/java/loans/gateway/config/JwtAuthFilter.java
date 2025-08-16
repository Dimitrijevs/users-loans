package loans.gateway.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Configuration
@Order(1)
public class JwtAuthFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        System.out.println("Processing request for path: " + path); // Debug log

        // // Skip JWT validation for public endpoints
        // if (isPublicPath(path)) {
        //     System.out.println("Public path detected, skipping JWT validation"); // Debug log
        //     return chain.filter(exchange);
        // }

        // String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        //     return this.unauthorized(exchange);
        // }

        // String token = authHeader.substring(7);

        // try {
        //     SecretKey key = this.key();

        //     Jwts.parser()
        //             .verifyWith(key)
        //             .build()
        //             .parseSignedClaims(token);

        // } catch (JwtException e) {
        //     return this.unauthorized(exchange);
        // }

        // If the token is valid, continue the filter chain
        return chain.filter(exchange);
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/v1/auth/") || 
               path.contains("/actuator/health") ||
               path.equals("/contact-support");
    }

    private SecretKey key () {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().setComplete();
    }
}
