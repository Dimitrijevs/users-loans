package loans.gateway;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

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
								// true - previous request * 2, but together max 1000ms, false - first request ( 100ms ) * request count ^ 2
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET).setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						.uri("lb://USERS"))
				.route(p -> p
						.path("/api/v1/loans/**")
						.filters(f -> f.circuitBreaker(config -> config.setName("loansCircuitBreaker")
								.setFallbackUri("forward:/contact-support"))
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET).setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						.uri("lb://LOANS"))
				.build();
	}
}
