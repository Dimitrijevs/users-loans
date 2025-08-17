package loans.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import loans.auth.dto.UserDTO;

@FeignClient(name = "users", path = "/api/v1/users")
public interface UserClient {

    @GetMapping("/check/{userEmail}")
    public boolean checkIfUserExists(@PathVariable String userEmail);

    @GetMapping("/find-with-password/{userEmail}")
    UserDTO findByEmailWithPassword(@PathVariable String userEmail);
}   
