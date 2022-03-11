package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.GenderEnum;
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

    private GenderEnum gender;

    private String devices;

    private String orders;

    private String productComments;

    private Long userCodeId;

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
