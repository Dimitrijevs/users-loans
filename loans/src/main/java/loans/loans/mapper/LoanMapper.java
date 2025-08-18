package loans.loans.mapper;

import org.springframework.stereotype.Component;

import loans.loans.dto.LoanRequest;
import loans.loans.dto.LoanResponse;
import loans.loans.dto.UserDTO;
import loans.loans.model.Loan;
import loans.loans.model.LoanStatus;

@Component
public class LoanMapper {

    public LoanResponse createDto(Loan loan, UserDTO user) {

        return LoanResponse.builder()
                .id(loan.getId())
                .userId(loan.getUserId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(loan.getEmail())
                .age(loan.getAge())
                .years(loan.getYears())
                .percentage(loan.getPercentage())
                .amount(loan.getAmount())
                .status(loan.getStatus())
                .build();
    }

    public Loan createLoan(UserDTO user, LoanRequest request, boolean hasApprovedLoan) {

        return Loan.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .age(user.getAge())
                .years(request.getYears())
                .percentage(request.getPercentage())
                .amount(request.getAmount())
                .status(hasApprovedLoan ? LoanStatus.REJECTED : LoanStatus.APPROVED)
                .build();
    }
}
