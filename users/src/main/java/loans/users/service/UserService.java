package loans.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import loans.users.dto.UserDTO;
import loans.users.dto.UserResponseDTO;
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

    private final PasswordEncoder passwordEncoder;

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

    public User create(UserDTO request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = userMapper.createUser(request);

        if (user == null) {
            throw new UserCreationException("Cannot create user from null or invalid data");
        }

        userRepository.save(user);

        return user;
    }

    // public UserDTO create(UserDTO request) {
    // // Hash password before creating user entity
    // request.setPassword(passwordEncoder.encode(request.getPassword()));

    // User user = userMapper.createUser(request);

    // if (user == null) {
    // throw new UserCreationException("Cannot create user from null or invalid
    // data");
    // }

    // User savedUser = userRepository.save(user);

    // UserDTO userDto = userMapper.createDto(savedUser);

    // // Clear password from response for security
    // userDto.setPassword(null);

    // return userDto;
    // }

    public boolean checkIfUserExists(String userEmail) {

        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userEmail);

        return userOptional.isPresent() ? true : false;
    }

    public UserDTO findByEmail(String userEmail) {

        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userEmail);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        User user = userOptional.get();

        return userMapper.createDto(user);
    }

    public User findByEmailWithPassword(String userEmail) {

        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userEmail);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        User user = userOptional.get();

        return user;
    }
}
