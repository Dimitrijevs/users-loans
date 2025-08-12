package loans.loans.metrics;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import loans.loans.model.LoanStatus;
import loans.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;


// custom metric
@Service
@RequiredArgsConstructor
public class LoanMetrics {

    private final LoanRepository loanRepository;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void initializeMetrics() {
        
        Gauge.builder("app_loans_approved_count", this.loanRepository, repo -> repo.countByStatus(LoanStatus.APPROVED))
             .description("The number of loans with APPROVED status in the database")
             .tag("service", "loan-service")
             .register(this.meterRegistry);
    }
}