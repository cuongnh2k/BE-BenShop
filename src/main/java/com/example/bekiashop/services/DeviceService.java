package com.example.bekiashop.services;

import com.example.bekiashop.dto.produces.TokenProduceDto;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {

    void updateToken(HttpServletRequest request, TokenProduceDto tokenProduceDto, String username);

    TokenProduceDto refreshToken(HttpServletRequest request);

    void logouts(String ids);

    void logout(HttpServletRequest request);
}
