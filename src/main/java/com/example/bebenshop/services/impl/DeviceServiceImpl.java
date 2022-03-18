package com.example.bebenshop.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bebenshop.dto.produces.TokenProduceDto;
import com.example.bebenshop.entities.DeviceEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.exceptions.UnauthorizedException;
import com.example.bebenshop.repository.DeviceRepository;
import com.example.bebenshop.repository.UserRepository;
import com.example.bebenshop.services.DeviceService;
import com.example.bebenshop.services.UserService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final UserRepository mUserRepository;
    private final DeviceRepository mDeviceRepository;
    private final UserService mUserService;
    private final ConvertUtil mConvertUtil;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.access.token.validity}")
    private Long JWT_ACCESS_TOKEN_VALIDITY;

    @Override
    public void updateToken(HttpServletRequest request, TokenProduceDto tokenProduceDto, String username) {
        UserEntity userEntity = mUserRepository.findByUsername(username);
        DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndUserId(request.getHeader(USER_AGENT), userEntity.getId());
        if (Objects.nonNull(deviceEntity)) {
            deviceEntity.setAccessToken(tokenProduceDto.getAccessToken());
            deviceEntity.setRefreshToken(tokenProduceDto.getRefreshToken());
        } else {
            deviceEntity = DeviceEntity.builder()
                    .userAgent(request.getHeader(USER_AGENT))
                    .accessToken(tokenProduceDto.getAccessToken())
                    .refreshToken(tokenProduceDto.getRefreshToken())
                    .user(userEntity)
                    .build();
        }
        mDeviceRepository.save(deviceEntity);
    }

    @Override
    public TokenProduceDto refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                String type = decodedJWT.getClaim("type").asString();
                if (type.equalsIgnoreCase("refresh")) {
                    UserEntity userEntity = mUserRepository.findByUsername(username);
                    TokenProduceDto tokenProduceDto = TokenProduceDto.builder()
                            .accessToken(JWT.create()
                                    .withSubject(userEntity.getUsername())
                                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY))
                                    .withIssuer(request.getRequestURL().toString())
                                    .withClaim("roles", userEntity.getRoles().stream().map(o -> o.getName().name()).collect(Collectors.toList()))
                                    .withClaim("type", "access")
                                    .sign(algorithm))
                            .refreshToken(refreshToken)
                            .build();
                    updateAccessToken(request, userEntity.getId(), tokenProduceDto);
                    return tokenProduceDto;
                } else {
                    throw new UnauthorizedException("Unauthorized");
                }
            } catch (Exception exception) {
                throw new UnauthorizedException(exception.getMessage());
            }
        } else {
            throw new UnauthorizedException("Unauthorized");
        }
    }

    public void updateAccessToken(HttpServletRequest request, Long userId, TokenProduceDto tokenProduceDto) {
        DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndUserIdAndRefreshToken(
                request.getHeader(USER_AGENT)
                , userId
                , tokenProduceDto.getRefreshToken());
        if (Objects.nonNull(deviceEntity)) {
            deviceEntity.setAccessToken(tokenProduceDto.getAccessToken());
            mDeviceRepository.save(deviceEntity);
        } else {
            throw new UnauthorizedException("Unauthorized");
        }
    }

    @Override
    public void logout(String ids) {
        List<DeviceEntity> deviceEntityList = mDeviceRepository.findAllById(mConvertUtil.toArray(ids));
        List<Long> idList = new ArrayList<>();
        deviceEntityList.forEach(o -> {
            if (o.getUser().getUsername().equals(mUserService.getUserName())) {
                idList.add(o.getId());
            }
        });
        mDeviceRepository.deleteAllById(idList);
    }
}
