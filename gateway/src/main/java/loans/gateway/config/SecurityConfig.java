package loans.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .authorizeExchange(exchanges -> exchanges
                        // Public endpoints - allow without auth
                        .pathMatchers("/actuator/health").permitAll()
                        .pathMatchers("/users/actuator/health").permitAll()
                        .pathMatchers("/loans/actuator/health").permitAll()

                        // Protected endpoints - require auth
                        .pathMatchers("/api/v1/users/**").hasRole("USERS")
                        .pathMatchers("/api/v1/loans/**").hasRole("LOANS")

                        // Default - require authentication for everything else
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthorityExtracter())))
                .csrf(csrf -> csrf.disable())
                .build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthorityExtracter() {

        // Create a standard Spring Security JWT converter
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        // Tell it to use OUR custom converter (KeycloakRoleConverter) to extract roles
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // Wrap it for reactive (WebFlux) use and return it
        // This makes it work with Spring WebFlux (reactive streams)
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
