package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.RoleEntity;
import com.example.bebenshop.entities.UserCodeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collection;
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

     @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderEntity> orders;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserCodeEntity userCodes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoleEntity> roles;
}