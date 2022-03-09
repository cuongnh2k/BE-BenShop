package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/device")
public class DeviceController extends BaseController {

    private final DeviceService mDeviceService;

    @DeleteMapping("/logout")
    public ResponseEntity<BaseResponseDto> logout(@RequestParam String ids) {
        mDeviceService.logout(ids);
        return success("Logout successful.");
    }
}
