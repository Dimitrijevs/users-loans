package loans.users.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.ws.rs.ServiceUnavailableException;
import loans.users.dto.UserDTO;
import loans.users.entity.User;
import loans.users.service.UserService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RateLimiter(name = "getAllUsers", fallbackMethod = "allUsersFallbackExample")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> allUsers() {
        return userService.allUsers();
    }

    public List<UserDTO> allUsersFallbackExample(Throwable throwable) throws ServiceUnavailableException {
       
        throw new ServiceUnavailableException("The user service is currently unavailable.");
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Validated UserDTO request) {
        
        userService.create(request);

        return "User created succesfully!";
    }

    @GetMapping("/{userEmail}")
    public UserDTO findByEmail(@PathVariable String userEmail) {
        return userService.findByEmail(userEmail);
    }

    @GetMapping("/find-with-password/{userEmail}")
    public User findByEmailWithPassword(@PathVariable String userEmail) {
        return userService.findByEmailWithPassword(userEmail);
    }

    @GetMapping("/check/{userEmail}")
    public boolean checkIfUserExists(@PathVariable String userEmail) {
        return userService.checkIfUserExists(userEmail);
    }
}

