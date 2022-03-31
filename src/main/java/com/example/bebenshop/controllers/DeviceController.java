package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/device")
public class DeviceController extends BaseController {

    private final DeviceService mDeviceService;

    @DeleteMapping("/logouts")
    public ResponseEntity<BaseResponseDto> logouts(@RequestParam String ids) {
        mDeviceService.logouts(ids);
        return success("Logout successful");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<BaseResponseDto> logout(HttpServletRequest request) {
        mDeviceService.logout(request);
        return success("Logout successful");
    }
}
