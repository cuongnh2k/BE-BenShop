package click.benshop.bebenshop.controllers;

import click.benshop.bebenshop.bases.BaseController;
import click.benshop.bebenshop.bases.BaseResponseDto;
import click.benshop.bebenshop.config.TokenConfig;
import click.benshop.bebenshop.config.UserDetailServiceConfig;
import click.benshop.bebenshop.dto.consumes.LoginConsumeDto;
import click.benshop.bebenshop.dto.produces.TokenProduceDto;
import click.benshop.bebenshop.exceptions.BadRequestException;
import click.benshop.bebenshop.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/auth")
public class AuthController extends BaseController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final TokenConfig mTokenConfig;
    private final DeviceService mDeviceService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(
            @RequestBody LoginConsumeDto loginConsumeDto
            , HttpServletRequest request) {
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
}
