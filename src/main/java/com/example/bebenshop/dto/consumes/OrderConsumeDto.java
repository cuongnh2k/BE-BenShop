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
    private OrderStatusEnum status;
    private Long userId;

    public OrderEntity toOrderEntity(){
        return OrderEntity.builder()
                .status(status)
                .build();
    }
}
