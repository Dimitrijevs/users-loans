package loans.loans.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import loans.loans.client.UserClient;
import loans.loans.dto.UserDTO;

@Component
public class Fallback implements UserClient {

    @Override
    public UserDTO findByEmail(@PathVariable String userEmail) {
        // throw new UserNotFoundException("Error occurred, please contact support!");
        return null;
    }
}
