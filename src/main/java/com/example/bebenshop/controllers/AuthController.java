package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.config.TokenConfig;
import com.example.bebenshop.config.UserDetailServiceConfig;
import com.example.bebenshop.dto.consumes.LoginConsumeDto;
import com.example.bebenshop.dto.produces.DeviceProduceDto;
import com.example.bebenshop.dto.produces.TokenProduceDto;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.DeviceMapper;
import com.example.bebenshop.mapper.UserMapper;
import com.example.bebenshop.repository.DeviceRepository;
import com.example.bebenshop.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/auth")
public class AuthController extends BaseController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final TokenConfig mTokenConfig;
    private final DeviceService mDeviceService;
    private final DeviceRepository mDeviceRepository;
    private final DeviceMapper mDeviceMapper;
    private final UserMapper mUserMapper;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestBody LoginConsumeDto loginConsumeDto, HttpServletRequest request) {
        final UserDetails userDetails = mUserDetailServiceConfig.loadUserByUsername(loginConsumeDto.getUsername());
        authenticate(userDetails.getUsername(), loginConsumeDto.getPassword());
        TokenProduceDto tokenProduceDto = mTokenConfig.generateToken(userDetails, request);
        mDeviceService.updateToken(request, tokenProduceDto, userDetails.getUsername());
        return success(tokenProduceDto, "Login successful.");
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new BadRequestException("Incorrect password.");
        }
    }

    @PatchMapping("/refresh-token")
    public ResponseEntity<BaseResponseDto> refreshToken(HttpServletRequest request) {
        return success(mDeviceService.refreshToken(request), "Refresh token successful.");
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAllDevice() {
        return success(mDeviceRepository.findAll().stream().map((o) -> {
            DeviceProduceDto deviceProduceDto = mDeviceMapper.toDeviceProduceDto(o);
            deviceProduceDto.setUser(mUserMapper.toUserProduceDto(o.getUser()));
            return deviceProduceDto;
        }).collect(Collectors.toList()), "Get data successful.");
    }
}
