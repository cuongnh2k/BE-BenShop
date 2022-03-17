package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderDetailProduceDto extends BaseProduceDto<Long> {

    private Integer quantity;

    private BigDecimal price;

    private Integer discount;

    private OrderProduceDto order;

    private ProductProduceDto product;

    private BigDecimal money;
}
