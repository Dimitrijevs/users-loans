package loans.users.mapper;

import org.springframework.stereotype.Component;

import loans.users.dto.UserDTO;
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
                .build();
    }
}
