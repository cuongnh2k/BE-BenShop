package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConsumeDto implements Serializable {

    private Long userId;

    private String orderDetails;

    private String orderNotes;

    public OrderEntity toOrderEntity() {
        return OrderEntity.builder()
                .status(OrderStatusEnum.PENDING)
                .build();
    }
}
