package loans.gateway.config;

import java.time.Duration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/api/v1/users/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("usersCircuitBreaker")
                                .setFallbackUri("forward:/contact-support"))
                                // Duration.ofMillis(100) - waiting time after a first fail
                                // Duration.ofMillis(1000) - max waiting time, no mater of a retry count
                                // 2 - multiplier ( * ) or factor ( ^ )
                                // true - previous request * 2, but together max 1000ms, false - first request (
                                // 100ms ) * request count ^ 2
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://USERS"))
                .route(p -> p
                        .path("/users/actuator/health")
                        .filters(f -> f.setPath("/actuator/health"))
                        .uri("lb://USERS"))
                .route(p -> p
                        .path("/api/v1/loans/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("loansCircuitBreaker")
                                .setFallbackUri("forward:/contact-support"))
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/loans/actuator/health")
                        .filters(f -> f.setPath("/actuator/health"))
                        .uri("lb://LOANS"))
                .route(p -> p 
                        .path("/api/v1/auth/**")
                        .filters(f -> f.circuitBreaker(config -> config.setName("authCircuitBreaker")
                                .setFallbackUri("forward:/contact-support")))
                        .uri("lb://AUTH"))
                .build();
    }
}
