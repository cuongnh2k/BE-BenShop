package com.example.bekiashop.dto.consumes;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  LoginConsumeDto implements Serializable {

    private String username;

    private String password;
}
