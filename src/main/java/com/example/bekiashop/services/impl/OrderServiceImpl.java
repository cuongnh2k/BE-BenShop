package com.example.bekiashop.services.impl;


import com.example.bekiashop.bases.BaseListProduceDto;
import com.example.bekiashop.dto.consumes.OrderConsumeDto;
import com.example.bekiashop.dto.consumes.OrderDetailConsumeDto;
import com.example.bekiashop.dto.produces.OrderDetailProduceDto;
import com.example.bekiashop.dto.produces.OrderProduceDto;
import com.example.bekiashop.dto.produces.ProductProduceDto;
import com.example.bekiashop.entities.OrderDetailEntity;
import com.example.bekiashop.entities.OrderDetailNoteEntity;
import com.example.bekiashop.entities.OrderEntity;
import com.example.bekiashop.entities.ProductEntity;
import com.example.bekiashop.enums.OrderStatusEnum;
import com.example.bekiashop.exceptions.BadRequestException;
import com.example.bekiashop.exceptions.ForbiddenException;
import com.example.bekiashop.mapper.*;
import com.example.bekiashop.repository.OrderDetailNoteRepository;
import com.example.bekiashop.repository.OrderDetailRepository;
import com.example.bekiashop.repository.OrderRepository;
import com.example.bekiashop.repository.ProductRepository;
import com.example.bekiashop.services.OrderService;
import com.example.bekiashop.services.UserService;
import com.example.bekiashop.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public BaseListProduceDto<OrderProduceDto> searchOrder(
            String orderStatusEnum
            , Long orderId
            , Optional<Long> startTime
            , Optional<Long> endTime
            , Pageable pageable) {
        Page<OrderEntity> orderEntityPage;
        if (mUserService.isRoleAdmin()) {
            orderEntityPage = mOrderRepository.searchOrderAdmin(
                    orderStatusEnum
                    , orderId
                    , DateUtil.convertToLocalDateTime(startTime.orElse(0L))
                    , endTime.orElse(null) == null ? LocalDateTime.now() : DateUtil.convertToLocalDateTime(endTime.orElse(null))
                    , pageable);
        } else {
            orderEntityPage = mOrderRepository.searchOrderUser(
                    orderStatusEnum
                    , orderId
                    , mUserService.getCurrentUser().getId()
                    , DateUtil.convertToLocalDateTime(startTime.orElse(0L))
                    , endTime.orElse(null) == null ? LocalDateTime.now() : DateUtil.convertToLocalDateTime(endTime.orElse(null))
                    , pageable);
        }
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

    @Override
    public OrderProduceDto getById(Long id) {
        OrderEntity orderEntity = mOrderRepository.findById(id).orElse(null);
        if (orderEntity == null) {
            throw new BadRequestException("order does not exist");
        }
        return toOrderProduceDto(orderEntity);
    }

    @Override
    public Long totalRevenue(
            String orderStatusEnum
            , Long orderId
            , Optional<Long> startTime
            , Optional<Long> endTime) {

        return mOrderRepository.totalRevenue(
                orderStatusEnum
                , orderId
                , DateUtil.convertToLocalDateTime(startTime.orElse(0L))
                , endTime.orElse(null) == null ? LocalDateTime.now() : DateUtil.convertToLocalDateTime(endTime.orElse(null))).orElse(0L);
    }

    @Override
    public OrderProduceDto updateStatusAdmin(Long id, OrderStatusEnum orderStatusEnum) {
        OrderEntity orderEntity = mOrderRepository.findById(id).orElse(null);
        if (orderEntity == null) {
            throw new BadRequestException("order does not exist");
        }
        orderEntity.setStatus(orderStatusEnum);
        return toOrderProduceDto(mOrderRepository.save(orderEntity));
    }
}
