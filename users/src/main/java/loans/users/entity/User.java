package loans.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

@Table(name = "_user")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long.")
    private String firstname;

    @NotEmpty
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long.")
    private String lastname;

    @Email
    @NotEmpty
    @Column(unique = true)
    @Size(max = 255, message = "Email have to be not more than 255 characters in length.")
    private String email;

    @NotNull
    @Min(value = 18, message = "User must be at least 18 years old.")
    @Max(value = 80, message = "User age cannot exceed 80 years.")
    private Integer age;
}
