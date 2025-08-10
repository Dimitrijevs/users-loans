package loans.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import loans.users.dto.UserDTO;
import loans.users.entity.User;
import loans.users.exception.UserCreationException;
import loans.users.exception.UserNotFoundException;
import loans.users.mapper.UserMapper;
import loans.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<UserDTO> allUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .age(user.getAge())
                        .build())
                .toList();
    }

    public UserDTO create(UserDTO request) {
        User user = userMapper.createUser(request);

        if (user == null) {
            throw new UserCreationException("Cannot create user from null or invalid data");
        }

        userRepository.save(user);

        UserDTO userDto = userMapper.createDto(user);

        return userDto;
    }

    public UserDTO findByEmail(String userEmail) {

        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userEmail);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        User user = userOptional.get();

        return userMapper.createDto(user);
    }
}
