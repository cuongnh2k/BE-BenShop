package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailProduceDto;
import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.OrderDetailMapper;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository mProductRepository;
    private final OrderDetailRepository mOrderDetailRepository;
    private final OrderRepository mOrderRepository;
    private final UserService mUserService;
    private final OrderDetailMapper mOrderDetailMapper;

    @Override
    public OrderDetailProduceDto addToCart(OrderDetailConsumeDto orderDetailConsumeDto) {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(orderDetailConsumeDto.getProductId());
        if (productEntity == null) {
            throw new BadRequestException("id does not exist");
        }
        UserEntity userEntity = mUserService.getCurrentUser();
        OrderEntity orderEntity = mOrderRepository.findByStatusAndUserId(
                OrderStatusEnum.IN_CART
                , userEntity.getId());

        if (orderEntity == null) {
            orderEntity = OrderEntity.builder()
                    .status(OrderStatusEnum.IN_CART)
                    .user(userEntity)
                    .build();
            mOrderRepository.save(orderEntity);
        }

        OrderDetailEntity orderDetailEntity = mOrderDetailRepository.save(OrderDetailEntity.builder()
                .quantity(orderDetailConsumeDto.getQuantity())
                .price(productEntity.getPrice())
                .discount(productEntity.getDiscount())
                .order(orderEntity)
                .product(productEntity)
                .build());
        return mOrderDetailMapper.toOrderDetailProduceDto(orderDetailEntity);
    }
}
