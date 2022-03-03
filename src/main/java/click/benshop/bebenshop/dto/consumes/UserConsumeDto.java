package click.benshop.bebenshop.dto.consumes;

import click.benshop.bebenshop.entities.UserEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserConsumeDto implements Serializable {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .avatar(avatar)
                .build();
    }
}
