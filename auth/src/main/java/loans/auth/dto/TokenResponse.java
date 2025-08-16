package loans.auth.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long expiresIn; 

    public TokenResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
