package loans.auth.dto;

import lombok.Data;

@Data
public class TokenRequest {

    // access or refresh token
    private String token;
}
