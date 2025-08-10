package loans.loans.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    // loan
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // user
    @NotNull
    private Integer userId;

    // user
    @Email
    @NotEmpty
    @Size(max = 255, message = "Email have to be not more than 255 characters in length.")
    private String email;

    // user
    @NotNull
    @Min(value = 18, message = "User must be at least 18 years old.")
    @Max(value = 80, message = "User age cannot exceed 80 years.")
    private Integer age;

    // loan
    @NotNull
    @Min(value = 2, message = "Minimum lenght of a loan is 2 years.")
    @Max(value = 20, message = "Maximum lenght of a loan is 20 years.")
    private Integer years;

    // loan
    @NotNull
    private Float percentage;

    // loan
    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}
