package com.example.bekiashop.repository;

import com.example.bekiashop.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    DeviceEntity findByUserAgentAndUserId(String userAgent, Long userId);

    DeviceEntity findByUserAgentAndUserIdAndRefreshToken(String userAgent, Long userId, String refreshToken);

    DeviceEntity findByUserAgentAndAccessToken(String userAgent, String accessToken);
}
