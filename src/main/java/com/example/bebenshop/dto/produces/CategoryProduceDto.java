package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
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
public class CategoryProduceDto extends BaseProduceDto<Long> {
    private String name;
}
