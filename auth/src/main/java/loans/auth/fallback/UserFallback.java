package loans.auth.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import loans.auth.client.UserClient;
import loans.auth.dto.UserDTO;

@Component
public class UserFallback implements UserClient {

    @Override
    public boolean checkIfUserExists(@PathVariable String userEmail) {
        return false;
    }

    @Override
    public UserDTO findByEmailWithPassword(@PathVariable String userEmail) {
        // Return null or throw an exception based on your needs
        throw new RuntimeException("User service is currently unavailable");
    }
}
