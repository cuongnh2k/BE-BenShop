package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.enums.RoleEnum;
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
