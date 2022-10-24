package com.example.bekiashop.dto.consumes;

import com.example.bekiashop.entities.DeviceEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceConsumeDto implements Serializable {

    private String userAgent;

    private String accessToken;

    private String refreshToken;

    private Long userId;

    public DeviceEntity toDeviceEntity() {
        return DeviceEntity.builder()
                .userAgent(userAgent)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
