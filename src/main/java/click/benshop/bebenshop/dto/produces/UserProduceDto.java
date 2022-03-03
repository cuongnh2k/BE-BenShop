package click.benshop.bebenshop.dto.produces;

import click.benshop.bebenshop.bases.BaseProduceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserProduceDto extends BaseProduceDto<Long> {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DeviceProduceDto> devices;
}