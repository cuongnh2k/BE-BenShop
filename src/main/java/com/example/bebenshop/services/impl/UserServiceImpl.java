package com.example.bebenshop.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bebenshop.dto.produces.UserProduceDto;
import com.example.bebenshop.entities.RoleEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.RoleEnum;
import com.example.bebenshop.mapper.DeviceMapper;
import com.example.bebenshop.mapper.UserMapper;
import com.example.bebenshop.repository.RoleRepository;
import com.example.bebenshop.repository.UserRepository;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;
    private final RoleRepository mRoleRepository;
    private final PasswordEncoder mPasswordEncoder;
    private final HttpServletRequest mHttpServletRequest;
    private final UserMapper mUserMapper;
    private final DeviceMapper mDeviceMapper;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public void createAdmin(UserEntity userEntity) {
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        List<RoleEntity> roleEntityList = mRoleRepository.findAll();
        userEntity.setRoles(roleEntityList);
        mUserRepository.save(userEntity);
    }

    @Override
    public void createUser(UserEntity userEntity) {
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        List<RoleEntity> roleEntityList = mRoleRepository.findAllById(Collections.singleton(2L));
        userEntity.setRoles(roleEntityList);
        mUserRepository.save(userEntity);
    }

    @Override
    public String getUserName() {
        String authorizationHeader = mHttpServletRequest.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        return decodedJWT.getSubject();
    }

    @Override
    public Boolean isRoleAdmin() {
        return mUserRepository.findByUsername(getUserName()).getRoles().stream().anyMatch(o ->
                o.getName().equals(RoleEnum.ROLE_ADMIN));
    }

    @Override
    public UserEntity getCurrentUser() {
        return mUserRepository.findByUsername(getUserName());
    }

    @Override
    public UserProduceDto getUserDetail() {
        UserEntity userEntity = mUserRepository.findByUsernameAndDeletedFlagFalse(getUserName());
        UserProduceDto userProduceDto = mUserMapper.toUserProduceDto(userEntity);
        userProduceDto.setDevices(userEntity.getDevices().stream()
                .map(mDeviceMapper::toDeviceProduceDto).collect(Collectors.toList()));
        return userProduceDto;
    }
}
