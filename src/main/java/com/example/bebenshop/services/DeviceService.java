package com.example.bebenshop.services;

import com.example.bebenshop.dto.produces.TokenProduceDto;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {

    void updateToken(HttpServletRequest request, TokenProduceDto tokenProduceDto, String username);

    TokenProduceDto refreshToken(HttpServletRequest request);

    void logout(String ids);
}
