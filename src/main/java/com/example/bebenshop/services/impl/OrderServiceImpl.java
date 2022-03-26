package com.example.bebenshop.services.impl;


import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailProduceDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.*;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.repository.OrderDetailNoteRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository mOrderRepository;
    private final OrderDetailNoteRepository mOrderNoteRepository;
    private final OrderDetailRepository mOrderDetailRepository;
    private final UserService mUserService;
    private final OrderDetailMapper mOrderDetailMapper;
    private final OrderMapper mOrderMapper;
    private final ProductMapper mProductMapper;
    private final ProductImageMapper mProductImageMapper;
    private final OrderDetailNoteMapper mOrderNoteMapper;

    @Override
    public OrderProduceDto createOrder(OrderConsumeDto orderConsumeDto) {

        return null;
    }

    @Override
    public OrderProduceDto cancelOrder(Long id) {
        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);
        if (orderEntity == null) {
            throw new BadRequestException("No order exists");
        }
        if (orderEntity.getCreatedBy().equals(mUserService.getUserName())
                || (!orderEntity.getStatus().name().equalsIgnoreCase(OrderStatusEnum.PENDING.name()))) {
            throw new ForbiddenException("Forbidden");
        }
        orderEntity.setStatus(OrderStatusEnum.CANCELED);
        mOrderRepository.save(orderEntity);
        return getOrderProduceDto(orderEntity);
    }

    @Override
    public BaseListProduceDto<OrderProduceDto> searchOrder(OrderStatusEnum orderStatusEnum, Pageable pageable) {
        Page<OrderEntity> orderEntityPage = mOrderRepository.findByStatusAndUserIdAndDeletedFlagFalse(
                orderStatusEnum
                , mUserService.getCurrentUser().getId()
                , pageable);
        List<OrderProduceDto> orderProduceDtoList = orderEntityPage.getContent().stream().map(o -> {
            OrderProduceDto orderProduceDto = mOrderMapper.toOrderProduceDto(o);
            orderProduceDto.setOrderNotes(o.getOrderNotes().stream()
                    .map(mOrderNoteMapper::toOrderNoteProduceDto).collect(Collectors.toList()));
            orderProduceDto.setOrderDetails(o.getOrderDetails().stream().map(oo -> {
                OrderDetailProduceDto orderDetailProduceDto = mOrderDetailMapper.toOrderDetailProduceDto(oo);
                orderDetailProduceDto.setMoney(oo.getPrice()
                        .divide(BigDecimal.valueOf(100))
                        .multiply(BigDecimal.valueOf(100 - oo.getDiscount()))
                        .multiply(BigDecimal.valueOf(oo.getQuantity())));
                ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(oo.getProduct());
                productProduceDto.setProductImages(oo.getProduct().getProductImages().stream()
                        .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));
                orderDetailProduceDto.setProduct(productProduceDto);
                return orderDetailProduceDto;
            }).collect(Collectors.toList()));
            return orderProduceDto;
        }).collect(Collectors.toList());
        return BaseListProduceDto.<OrderProduceDto>builder()
                .content(orderProduceDtoList)
                .totalElements(orderEntityPage.getTotalElements())
                .totalPages(orderEntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    @Override
    public OrderProduceDto updateStatusAdmin(Long id, OrderStatusEnum orderStatusEnum) {
        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);
        if (orderEntity == null) {
            throw new BadRequestException("Order does not exist");
        }
        if (!orderStatusEnum.name().equalsIgnoreCase(OrderStatusEnum.RESOLVED.name())
                && !orderStatusEnum.name().equalsIgnoreCase(OrderStatusEnum.COMPLETED.name())
                && !orderStatusEnum.name().equalsIgnoreCase(OrderStatusEnum.CANCELED.name())) {
            throw new ForbiddenException("Forbidden");
        }
        orderEntity.setStatus(orderStatusEnum);
        mOrderRepository.save(orderEntity);
        return getOrderProduceDto(orderEntity);
    }

    private OrderProduceDto getOrderProduceDto(OrderEntity orderEntity) {
        OrderProduceDto orderProduceDto = mOrderMapper.toOrderProduceDto(orderEntity);
        orderProduceDto.setOrderDetails(orderEntity.getOrderDetails().stream().map(o -> {
            OrderDetailProduceDto orderDetailProduceDto = mOrderDetailMapper.toOrderDetailProduceDto(o);
            orderDetailProduceDto.setMoney(o.getPrice()
                    .divide(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(100 - o.getDiscount()))
                    .multiply(BigDecimal.valueOf(o.getQuantity())));
            return orderDetailProduceDto;
        }).collect(Collectors.toList()));
        return orderProduceDto;
    }
}
