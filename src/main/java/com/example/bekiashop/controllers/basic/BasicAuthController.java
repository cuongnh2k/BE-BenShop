package com.example.bekiashop.controllers.basic;

import com.example.bekiashop.bases.BaseController;
import com.example.bekiashop.bases.BaseResponseDto;
import com.example.bekiashop.config.TokenConfig;
import com.example.bekiashop.config.UserDetailServiceConfig;
import com.example.bekiashop.dto.consumes.LoginConsumeDto;
import com.example.bekiashop.dto.consumes.UserConsumeDto;
import com.example.bekiashop.dto.produces.TokenProduceDto;
import com.example.bekiashop.exceptions.BadRequestException;
import com.example.bekiashop.services.DeviceService;
import com.example.bekiashop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/auth")
public class BasicAuthController extends BaseController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final TokenConfig mTokenConfig;
    private final DeviceService mDeviceService;
    private final UserService mUserService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestBody LoginConsumeDto loginConsumeDto, HttpServletRequest request) {
        final UserDetails userDetails = mUserDetailServiceConfig.loadUserByUsername(loginConsumeDto.getUsername());
        authenticate(userDetails.getUsername(), loginConsumeDto.getPassword());
        TokenProduceDto tokenProduceDto = mTokenConfig.generateToken(userDetails, request);
        mDeviceService.updateToken(request, tokenProduceDto, userDetails.getUsername());
        return success(tokenProduceDto, "Login successful");
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new BadRequestException("Incorrect password");
        }
    }

    @PatchMapping("/refresh-token")
    public ResponseEntity<BaseResponseDto> refreshToken(HttpServletRequest request) {
        return success(mDeviceService.refreshToken(request), "Refresh token successful");
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto> createRegister(@RequestBody UserConsumeDto userConsumeDto){
        return created(mUserService.createRegister(userConsumeDto),"Create user successful");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponseDto> resetPassword(@RequestBody LoginConsumeDto loginConsumeDto) throws MessagingException {
        mUserService.resetPassword(loginConsumeDto.getUsername());
        return success("Reset password successful");
    }
}
