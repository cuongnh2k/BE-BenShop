package click.benshop.bebenshop.services;

import click.benshop.bebenshop.bases.BaseListProduceDto;
import click.benshop.bebenshop.dto.produces.DeviceProduceDto;
import click.benshop.bebenshop.dto.produces.TokenProduceDto;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {

    void updateToken(HttpServletRequest request, TokenProduceDto tokenProduceDto, String username);

    TokenProduceDto refreshToken(HttpServletRequest request);

    void logout(String ids);

    BaseListProduceDto<DeviceProduceDto> getAllDevice(Pageable pageable);
}
