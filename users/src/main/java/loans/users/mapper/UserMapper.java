package loans.users.mapper;

import org.springframework.stereotype.Component;

import loans.users.dto.UserDTO;
import loans.users.dto.UserResponseDTO;
import loans.users.entity.User;

@Component
public class UserMapper {

    public User createUser(UserDTO dto) {

        if (dto == null) {
            return null;
        }

        return User.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .age(dto.getAge())
                .role(dto.getRole())
                .password(dto.getPassword())
                .build();
    }

    public UserDTO createDto(User entity) {

        if (entity == null) {
            return null;
        }

        return UserDTO.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .age(entity.getAge())
                .role(entity.getRole())
                .build();
    }

    public UserResponseDTO createUserResponseDTO(User user) {

        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .age(user.getAge())
                .role(user.getRole())
                .build();
    }
}
