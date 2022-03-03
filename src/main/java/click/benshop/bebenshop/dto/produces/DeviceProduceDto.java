package click.benshop.bebenshop.dto.produces;

import click.benshop.bebenshop.bases.BaseProduceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DeviceProduceDto extends BaseProduceDto<Long> {

    private String userAgent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserProduceDto user;
}
