package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.OrderDetailEntity;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetailConsumeDto implements Serializable {

    private Integer quantity;

    private BigDecimal price;

    private Integer discount;

    private Long orderId;

    private Long productId;

    public OrderDetailEntity toOrderDetailEntity() {
        return OrderDetailEntity.builder()
                .quantity(quantity)
                .price(price)
                .discount(discount)
                .build();
    }
}
