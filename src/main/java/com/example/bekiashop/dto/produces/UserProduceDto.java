package com.example.bekiashop.dto.produces;

import com.example.bekiashop.bases.BaseProduceDto;
import com.example.bekiashop.enums.GenderEnum;
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

    private GenderEnum gender;

    private List<DeviceProduceDto> devices;

    private List<OrderProduceDto> orders;

    private List<RoleProduceDto> roles;

    private List<ProductCommentProduceDto> productComments;
}