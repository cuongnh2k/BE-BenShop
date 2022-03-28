package com.example.bebenshop.services.impl;


import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailProduceDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.entities.OrderDetailNoteEntity;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.*;
import com.example.bebenshop.repository.OrderDetailNoteRepository;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.repository.ProductRepository;
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
    private final ProductRepository mProductRepository;
    private final OrderDetailRepository mOrderDetailRepository;
    private final OrderDetailNoteRepository mOrderDetailNoteRepository;
    private final UserService mUserService;
    private final OrderDetailMapper mOrderDetailMapper;
    private final OrderMapper mOrderMapper;
    private final OrderDetailNoteMapper mOrderDetailNoteMapper;
    private final UserMapper mUserMapper;
    private final ProductMapper mProductMapper;
    private final ProductImageMapper mProductImageMapper;

    public OrderProduceDto toOrderProduceDto(OrderEntity orderEntity) {
        OrderProduceDto orderProduceDto = mOrderMapper.toOrderProduceDto(orderEntity);
        orderProduceDto.setUser(mUserMapper.toUserProduceDto(orderEntity.getUser()));
        orderProduceDto.setOrderDetails(orderEntity.getOrderDetails().stream().map(o -> {
            OrderDetailProduceDto orderDetailProduceDto = mOrderDetailMapper.toOrderDetailProduceDto(o);
            orderDetailProduceDto.setMoney(o.getPrice()
                    .divide(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(100 - o.getDiscount()))
                    .multiply(BigDecimal.valueOf(o.getQuantity())));
            orderDetailProduceDto.setOrderDetailNotes(o.getOrderDetailNotes().stream()
                    .map(mOrderDetailNoteMapper::toOrderNoteProduceDto).collect(Collectors.toList()));

            ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(o.getProduct());
            productProduceDto.setProductImages(o.getProduct().getProductImages().stream()
                    .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));

            orderDetailProduceDto.setProduct(productProduceDto);
            return orderDetailProduceDto;
        }).collect(Collectors.toList()));
        return orderProduceDto;
    }

    @Override
    public OrderProduceDto createOrder(OrderConsumeDto orderConsumeDto) {

        OrderEntity orderEntity = OrderEntity.builder()
                .status(OrderStatusEnum.PENDING)
                .user(mUserService.getCurrentUser())
                .build();
        mOrderRepository.save(orderEntity);

        for (OrderDetailConsumeDto o : orderConsumeDto.getOrderDetails()) {
            ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(o.getId());
            if (productEntity == null) {
                throw new BadRequestException("Product with id" + o.getId() + " does not exist");
            }

            OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                    .product(productEntity)
                    .discount(productEntity.getDiscount())
                    .price(productEntity.getPrice())
                    .quantity(o.getQuantity())
                    .order(orderEntity)
                    .build();
            mOrderDetailRepository.save(orderDetailEntity);

            mOrderDetailNoteRepository.save(OrderDetailNoteEntity.builder()
                    .content(o.getDescription())
                    .orderDetail(orderDetailEntity)
                    .build());
        }
        return null;
    }

    @Override
    public OrderProduceDto cancelOrder(Long id) {
        OrderEntity orderEntity = mOrderRepository.findById(id).orElse(null);
        if (orderEntity == null) {
            throw new BadRequestException("Order does not exist");
        }
        if (!orderEntity.getCreatedBy().equals(mUserService.getUserName())
                || !orderEntity.getStatus().name().equalsIgnoreCase(OrderStatusEnum.PENDING.name())) {
            throw new ForbiddenException("Forbidden");
        }
        orderEntity.setStatus(OrderStatusEnum.CANCELED);
        return toOrderProduceDto(mOrderRepository.save(orderEntity));
    }

    @Override
    public OrderProduceDto getOrderById(Long id) {
        OrderEntity orderEntity = mOrderRepository.findById(id).orElse(null);
        if (orderEntity == null) {
            throw new BadRequestException("Order does not exist");
        }
        if (!orderEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        return toOrderProduceDto(orderEntity);
    }

    @Override
    public BaseListProduceDto<OrderProduceDto> searchOrder(OrderStatusEnum orderStatusEnum, Pageable pageable) {
        Page<OrderEntity> orderEntityPage = mOrderRepository.findByStatusAndUserId(
                orderStatusEnum
                , mUserService.getCurrentUser().getId()
                , pageable);
        List<OrderProduceDto> orderProduceDtoList = orderEntityPage.getContent().stream()
                .map(this::toOrderProduceDto).collect(Collectors.toList());

        return BaseListProduceDto.<OrderProduceDto>builder()
                .content(orderProduceDtoList)
                .totalElements(orderEntityPage.getTotalElements())
                .totalPages(orderEntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }
}
