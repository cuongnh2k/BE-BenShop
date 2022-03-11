package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.UserCodeEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCodeConsumeDto implements Serializable {

    private String code;

    private Long userId;

    public UserCodeEntity toUserEntity() {
        return UserCodeEntity.builder()
                .code(code)
                .build();
    }
}
