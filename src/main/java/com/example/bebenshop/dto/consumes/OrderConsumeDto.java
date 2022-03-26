package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConsumeDto implements Serializable {

    private List<OrderDetailConsumeDto> orderDetails;

    public OrderEntity toOrderEntity() {
        return OrderEntity.builder()
                .status(OrderStatusEnum.PENDING)
                .build();
    }
}
