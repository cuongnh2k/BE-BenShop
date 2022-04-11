package com.example.bebenshop.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bebenshop.dto.consumes.UserConsumeDto;
import com.example.bebenshop.dto.produces.UserProduceDto;
import com.example.bebenshop.entities.DeviceEntity;
import com.example.bebenshop.entities.RoleEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.GenderEnum;
import com.example.bebenshop.enums.RoleEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.DeviceMapper;
import com.example.bebenshop.mapper.RoleMapper;
import com.example.bebenshop.mapper.UserMapper;
import com.example.bebenshop.repository.DeviceRepository;
import com.example.bebenshop.repository.RoleRepository;
import com.example.bebenshop.repository.UserRepository;
import com.example.bebenshop.services.UserService;
import com.example.bebenshop.util.SentEmailUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

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
    private final RoleMapper roleMapper;
    private final SentEmailUtil mSentEmailUtil;
    private final AuthenticationManager authenticationManager;
    private final DeviceRepository mDeviceRepository;

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
        UserEntity userEntity = mUserRepository.findByUsername(getUserName());
        UserProduceDto userProduceDto = mUserMapper.toUserProduceDto(userEntity);
        userProduceDto.setDevices(userEntity.getDevices().stream()
                .map(mDeviceMapper::toDeviceProduceDto).collect(Collectors.toList()));
        return userProduceDto;
    }

    @Override
    public UserProduceDto createRegister(UserConsumeDto userConsumeDto) {
        UserEntity userEntity = userConsumeDto.toUserEntity();
        if (mUserRepository.existsByUsername(userConsumeDto.getUsername())) {
            throw new BadRequestException("User name already exist");
        }
        if (mUserRepository.existsByEmail(userConsumeDto.getEmail())) {
            throw new BadRequestException("Email already exist");
        }
        userEntity.setRoles(mRoleRepository.findByName(RoleEnum.ROLE_USER));
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        mUserRepository.save(userEntity);
        UserProduceDto userProduceDto = mUserMapper.toUserProduceDto(userEntity);
        userProduceDto.setRoles(userEntity.getRoles().stream().map(roleMapper::toRoleProduceDto).collect(Collectors.toList()));
        return userProduceDto;
    }

    @Override
    public UserProduceDto editUser(HashMap<String, Object> map) {
        UserEntity userEntity = getCurrentUser();
        for (String i : map.keySet()) {
            switch (i) {
                case "firstName":
                    userEntity.setFirstName(map.get(i).toString());
                    break;
                case "lastName":
                    userEntity.setLastName(map.get(i).toString());
                    break;
                case "gender":
                    userEntity.setGender(GenderEnum.valueOf(map.get(i).toString()));
                    break;
            }
        }
        return mUserMapper.toUserProduceDto(mUserRepository.save(userEntity));
    }

    @Override
    public void resetPassword(String username) throws MessagingException {
        UserEntity userEntity = mUserRepository.findByUsernameOrEmail(username, username);
        if (userEntity == null)
            throw new BadRequestException("account does not exist");

        String gen = RandomStringUtils.randomAlphanumeric(8);
        userEntity.setPassword(mPasswordEncoder.encode(gen));

        mUserRepository.save(userEntity);

        mSentEmailUtil.senPasswordNew(userEntity.getEmail(), gen);
    }

    @Override
    public UserProduceDto editPasswordOrMail(HashMap<String, Object> map, HttpServletRequest request) {
        UserEntity userEntity = getCurrentUser();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEntity.getUsername(),
                            map.get("password").toString()));
        } catch (Exception e) {
            throw new BadRequestException("Incorrect password");
        }
        for (String i : map.keySet()) {
            switch (i) {
                case "passwordLatest":
                    userEntity.setPassword(mPasswordEncoder.encode(map.get(i).toString()));
                    break;
                case "email":
                    String email = map.get(i).toString();
                    UserEntity userEntity2 = mUserRepository.findByEmail(email);
                    if (userEntity2 != null && userEntity2.getId() != userEntity.getId()) {
                        throw new BadRequestException("Email " + email + " already used");
                    }
                    userEntity.setEmail(email);
                    break;
            }
        }
        DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndUserId(
                request.getHeader(USER_AGENT)
                , userEntity.getId());
        if (deviceEntity == null) {
            throw new BadRequestException("Device does not exist");
        }
        mDeviceRepository.delete(deviceEntity);
        return mUserMapper.toUserProduceDto(mUserRepository.save(userEntity));
    }
}