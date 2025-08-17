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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

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

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
}
