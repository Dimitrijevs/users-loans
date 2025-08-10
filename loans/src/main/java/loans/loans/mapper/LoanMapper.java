package loans.loans.mapper;

import org.springframework.stereotype.Component;

import loans.loans.dto.LoanResponse;
import loans.loans.dto.UserDTO;
import loans.loans.model.Loan;

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
                .status(loan.getStatus())
                .build();
    }
}
