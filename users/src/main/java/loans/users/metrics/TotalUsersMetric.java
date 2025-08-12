package loans.users.metrics;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import loans.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;

// custom metric
@Service
@RequiredArgsConstructor
public class TotalUsersMetric {

    private final UserRepository userRepository;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void initializeMetrics() {

        Gauge.builder("app_users_count", this.userRepository, repo -> repo.count())
             .description("The number of users in the database")
             .tag("service", "users-service")
             .register(this.meterRegistry);
    }
}
