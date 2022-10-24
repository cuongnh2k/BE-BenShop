package com.example.bekiashop.dto.consumes;

import com.example.bekiashop.enums.OrderStatusEnum;
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
    private OrderStatusEnum status;
}
