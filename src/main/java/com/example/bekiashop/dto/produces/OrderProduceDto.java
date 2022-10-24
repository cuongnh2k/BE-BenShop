package com.example.bekiashop.dto.produces;

import com.example.bekiashop.bases.BaseProduceDto;
import com.example.bekiashop.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderProduceDto extends BaseProduceDto<Long> {

    private OrderStatusEnum status;

    private UserProduceDto user;

    private List<OrderDetailProduceDto> orderDetails;

    private Long totalRevenue;
}
