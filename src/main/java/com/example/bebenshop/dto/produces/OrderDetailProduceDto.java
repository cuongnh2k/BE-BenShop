package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrderProduceDto order;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductProduceDto product;
}
