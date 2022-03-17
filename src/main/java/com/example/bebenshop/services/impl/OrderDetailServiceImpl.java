package com.example.bebenshop.services.impl;

import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.services.OrderDetailService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository mOrderDetailRepository;
    private final UserService mUserService;

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetailEntity orderDetailEntity = mOrderDetailRepository.findById(id).orElse(null);
        if (orderDetailEntity == null) {
            throw new BadRequestException("Product does not exist");
        }
        if (orderDetailEntity.getOrder().getUser().getId() != mUserService.getCurrentUser().getId()
                || !orderDetailEntity.getOrder().getStatus().name().equalsIgnoreCase(OrderStatusEnum.IN_CART.name())) {
            throw new ForbiddenException("Forbidden");
        }
        mOrderDetailRepository.deleteById(orderDetailEntity.getId());
    }
}
