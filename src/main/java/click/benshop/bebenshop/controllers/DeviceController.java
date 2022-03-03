package click.benshop.bebenshop.controllers;

import click.benshop.bebenshop.bases.BaseController;
import click.benshop.bebenshop.bases.BaseResponseDto;
import click.benshop.bebenshop.services.DeviceService;
import click.benshop.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/device")
public class DeviceController extends BaseController {

    private final DeviceService mDeviceService;
    private final ConvertUtil mConvertUtil;

    @DeleteMapping("/logout")
    public ResponseEntity<BaseResponseDto> logout(@RequestParam String ids) {
        mDeviceService.logout(ids);
        return success("Logout successful.");
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAllDevice(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) String order) {
        return success(mDeviceService.getAllDevice(mConvertUtil.buildPageable(page, size, order)), "Get data successful.");
    }
}
