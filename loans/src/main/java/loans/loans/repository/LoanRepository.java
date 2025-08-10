package loans.loans.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import loans.loans.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findAllByEmailIgnoreCase(String email);
}
