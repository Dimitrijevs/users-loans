package loans.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

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

    @NotEmpty
    private Integer age;

    @NotEmpty
    private String password;

}
