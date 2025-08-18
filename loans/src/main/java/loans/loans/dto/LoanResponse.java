package loans.loans.dto;

import java.math.BigDecimal;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import loans.loans.model.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponse {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotEmpty
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long.")
    private String firstname;

    @NotEmpty
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long.")
    private String lastname;

    @Email
    @NotEmpty
    @Size(max = 255, message = "Email have to be not more than 255 characters in length.")
    private String email;

    @NotNull
    @Min(value = 18, message = "User must be at least 18 years old.")
    @Max(value = 80, message = "User age cannot exceed 80 years.")
    private Integer age;

    @NotNull
    @Min(value = 2, message = "Minimum lenght of a loan is 2 years.")
    @Max(value = 20, message = "Maximum lenght of a loan is 20 years.")
    private Integer years;

    @NotNull
    private Float percentage;

    // loan
    @NotNull
    @Min(value = 5000, message = "Minimum loan amount is 5000 euros.")
    @Max(value = 5000000, message = "Maximum loan amount is 5000000 euros.")
    private BigDecimal amount; 

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}
