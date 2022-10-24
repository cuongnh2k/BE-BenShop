package com.example.bekiashop.dto.consumes;

import com.example.bekiashop.entities.UserEntity;
import com.example.bekiashop.enums.GenderEnum;
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

    private String passwordLatest;

    private String email;

    private String firstName;

    private String lastName;

    private String avatar;

    private GenderEnum gender;

    private String devices;

    private String orders;

    private String productComments;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .avatar(avatar)
                .gender(gender)
                .build();
    }
}
