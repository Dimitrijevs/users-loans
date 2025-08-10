package loans.loans.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequest {

    @Email
    @NotEmpty
    @Size(max = 255, message = "Email have to be not more than 255 characters in length.")
    private String email;

    @NotNull
    @Min(value = 2, message = "Minimum lenght of a loan is 2 years.")
    @Max(value = 20, message = "Maximum lenght of a loan is 20 years.")
    private Integer years;

    @NotNull
    private Float percentage;
}
