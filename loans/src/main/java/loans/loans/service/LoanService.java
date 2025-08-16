package loans.loans.service;

import java.util.List;

import org.springframework.stereotype.Service;

import loans.loans.client.UserClient;
import loans.loans.dto.LoanRequest;
import loans.loans.dto.LoanResponse;
import loans.loans.dto.UserDTO;
import loans.loans.exception.UserNotFoundException;
import loans.loans.mapper.LoanMapper;
import loans.loans.model.Loan;
import loans.loans.model.LoanStatus;
import loans.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final UserClient userClient;

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    public List<LoanResponse> seachByEmail(String userEmail) {
        UserDTO user = userClient.findByEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        return loanRepository.findAllByEmailIgnoreCase(user.getEmail())
                .stream()
                .map(loan -> loanMapper.createDto(loan, user))
                .toList();
    }

    public List<Loan> allLoans() {
        return loanRepository.findAll();
    }

    public LoanResponse create(LoanRequest request) {
        UserDTO user = userClient.findByEmail(request.getEmail());

        if (user == null) {
            throw new UserNotFoundException("test");
        }

        List<Loan> loans = loanRepository.findAllByEmailIgnoreCase(user.getEmail());

        boolean hasApprovedLoan = loans.stream()
            .anyMatch(loan -> loan.getStatus() == LoanStatus.APPROVED);

        Loan loan = Loan.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .age(user.getAge())
                .years(request.getYears())
                .percentage(request.getPercentage())
                .status(hasApprovedLoan ? LoanStatus.REJECTED : LoanStatus.APPROVED)
                .build();

        loanRepository.save(loan);

        return loanMapper.createDto(loan, user);
    }
}
