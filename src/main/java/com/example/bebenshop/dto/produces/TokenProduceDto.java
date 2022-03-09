package com.example.bebenshop.dto.produces;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenProduceDto implements Serializable {

    private String accessToken;
    private String refreshToken;
}
