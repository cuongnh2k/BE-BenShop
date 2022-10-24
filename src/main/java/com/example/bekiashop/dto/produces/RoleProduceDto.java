package com.example.bekiashop.dto.produces;

import com.example.bekiashop.bases.BaseProduceDto;
import com.example.bekiashop.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleProduceDto extends BaseProduceDto<Long> {

    private RoleEnum name;
}
