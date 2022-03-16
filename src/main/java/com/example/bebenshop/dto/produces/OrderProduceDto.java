package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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

    private List<OrderNoteProduceDto> orderNotes;
}
