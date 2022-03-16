package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailProduceDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.OrderDetailMapper;
import com.example.bebenshop.mapper.OrderMapper;
import com.example.bebenshop.mapper.ProductImageMapper;
import com.example.bebenshop.mapper.ProductMapper;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository mProductRepository;
    private final OrderDetailRepository mOrderDetailRepository;
    private final OrderRepository mOrderRepository;
    private final UserService mUserService;
    private final OrderMapper mOrderMapper;
    private final OrderDetailMapper mOrderDetailMapper;
    private final ProductMapper mProductMapper;
    private final ProductImageMapper mProductImageMapper;

    @Override
    public OrderProduceDto addToCart(OrderDetailConsumeDto orderDetailConsumeDto) {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(orderDetailConsumeDto.getProductId());
        if (productEntity == null) {
            throw new BadRequestException("id does not exist");
        }
        OrderDetailEntity orderDetailEntity = orderDetailConsumeDto.toOrderDetailEntity();
        orderDetailEntity.setPrice(productEntity.getPrice());
        orderDetailEntity.setDiscount(productEntity.getDiscount());
        orderDetailEntity.setProduct(productEntity);
        mOrderDetailRepository.save(orderDetailEntity);

        UserEntity userEntity = mUserService.getCurrentUser();

        OrderEntity orderEntity = mOrderRepository.findByStatusAndUserId(
                OrderStatusEnum.IN_CART
                , userEntity.getId());

        if (orderEntity == null) {
            orderEntity = OrderEntity.builder()
                    .orderDetails(Collections.singletonList(orderDetailEntity))
                    .user(userEntity)
                    .status(OrderStatusEnum.IN_CART)
                    .build();
        } else {
            List<OrderDetailEntity> orderDetailEntityList = (List<OrderDetailEntity>) orderEntity.getOrderDetails();
            orderDetailEntityList.add(orderDetailEntity);
            orderEntity.setOrderDetails(orderDetailEntityList);
        }
        mOrderRepository.save(orderEntity);

        OrderProduceDto orderProduceDto = mOrderMapper.toOrderProduceDto(orderEntity);
        BigDecimal totalMoney = new BigDecimal(0);
        orderProduceDto.setOrderDetails(orderEntity.getOrderDetails().stream()
                .map(o -> {
                    OrderDetailProduceDto orderDetailProduceDto = mOrderDetailMapper.toOrderDetailProduceDto(o);
                    ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(o.getProduct());
                    productProduceDto.setProductImages(o.getProduct().getProductImages().stream()
                            .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));
                    orderDetailProduceDto.setProduct(productProduceDto);

                    BigDecimal money = orderDetailProduceDto.getPrice()
                            .divide(BigDecimal.valueOf(100))
                            .multiply(BigDecimal.valueOf(100 - orderDetailProduceDto.getDiscount()));
                    totalMoney.add(money);
                    orderDetailProduceDto.setMoney(money);
                    return orderDetailProduceDto;
                }).collect(Collectors.toList()));
        orderProduceDto.setTotalMoney(totalMoney);
        return orderProduceDto;
    }
}
