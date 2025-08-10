package loans.loans.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import loans.loans.dto.UserDTO;
import loans.loans.fallback.Fallback;

@FeignClient(name = "users", path = "/api/v1/users", fallback = Fallback.class)
public interface UserClient {

    @GetMapping("/{userEmail}")
    UserDTO findByEmail(@PathVariable String userEmail);
}
