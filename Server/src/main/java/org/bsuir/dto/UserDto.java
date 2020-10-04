package org.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bsuir.model.Role;
import org.bsuir.model.User;

@Data
@NoArgsConstructor
public class UserDto extends User {
    private String roleName;

    @Builder(builderMethodName = "userDtoBuilder")
    public UserDto(Long idUser, String name, String surname, String email, String password, long role, String roleName) {
        super(idUser, name, surname, email, password, role);
        this.roleName = roleName;
    }

    public static UserDto of(User user, Role role) {
        return UserDto.userDtoBuilder()
                .idUser(user.getIdUser())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .roleName(role.getRoleName())
                .build();
    }

    public User createUser() {
        return User.builder()
                .idUser(getIdUser())
                .name(getName())
                .surname(getSurname())
                .email(getEmail())
                .password(getPassword())
                .role(getRole())
                .build();
    }
}
