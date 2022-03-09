package com.example.bebenshop.repository;

import com.example.bebenshop.entities.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    DeviceEntity findByUserAgentAndUserId(String userAgent, Long userId);

    DeviceEntity findByUserAgentAndUserIdAndRefreshToken(String userAgent, Long userId, String refreshToken);

    DeviceEntity findByUserAgentAndAccessToken(String userAgent, String accessToken);

    Page<DeviceEntity> findByUserId(Long userId, Pageable pageable);
}
