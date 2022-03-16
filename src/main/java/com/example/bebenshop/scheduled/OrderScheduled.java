package com.example.bebenshop.scheduled;

import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class OrderScheduled {

    private final OrderRepository mOrderRepository;

    @Scheduled(fixedRate = 86400000)
    public void deleteOrder() {
        mOrderRepository.deleteOrder(OrderStatusEnum.UNCONFIRMED.name());
    }
}
