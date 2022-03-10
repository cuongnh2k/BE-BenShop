package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleProduceDto extends BaseProduceDto<Long> {

    private RoleEnum name;
}
