package com.example.bebenshop.services.impl;



import com.example.bebenshop.bases.BaseListProduceDto;
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
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.*;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.services.UserService;
import com.example.bebenshop.util.SentEmailUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.math.BigDecimal;
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
    private final OrderDetailMapper mOrderDetailMapper;
    private final OrderMapper mOrderMapper;
    private final ProductMapper mProductMapper;
    private final ProductImageMapper mProductImageMapper;
    private final SentEmailUtil mSentEmailUtil;
    private final OrderNoteMapper mOrderNoteMapper;

    @Override
    public OrderDetailProduceDto addToCart(OrderDetailConsumeDto orderDetailConsumeDto) {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(orderDetailConsumeDto.getProductId());
        if (productEntity == null) {
            throw new BadRequestException("Product does not exist");
        }
        UserEntity userEntity = mUserService.getCurrentUser();
        OrderEntity orderEntity = mOrderRepository.findByStatusAndUserIdAndDeletedFlagFalse(
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

    @Override
    public OrderProduceDto addOrder() throws MessagingException {
        UserEntity userEntity = mUserService.getCurrentUser();
        OrderEntity orderEntity = mOrderRepository.findByStatusAndUserIdAndDeletedFlagFalse(
                OrderStatusEnum.IN_CART
                , userEntity.getId());
        if (orderEntity == null) {
            throw new BadRequestException("There are no products in the cart");
        }
        orderEntity.setStatus(OrderStatusEnum.UNCONFIRMED);
        String code = RandomStringUtils.random(6, "0123456789");
        orderEntity.setVerificationCode(code);
        mOrderRepository.save(orderEntity);

        mSentEmailUtil.verificationCode(userEntity.getEmail(), code);

        return getOrderProduceDto(orderEntity);
    }

    @Override
    public OrderProduceDto verificationOrder(String code, Long orderId) {
        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(orderId);
        if (orderEntity == null) {
            throw new BadRequestException("No order exists");
        }
        if (orderEntity.getUser().getId() != mUserService.getCurrentUser().getId()) {
            throw new ForbiddenException("Forbidden");
        }
        if (!orderEntity.getVerificationCode().equals(code)) {
            throw new BadRequestException("The verification code is incorrect");
        }
        orderEntity.setStatus(OrderStatusEnum.PENDING);
        mOrderRepository.save(orderEntity);

        return getOrderProduceDto(orderEntity);
    }

    @Override
    public OrderProduceDto cancelOrder(Long id) {
        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);
        if (orderEntity == null) {
            throw new BadRequestException("No order exists");
        }
        if (orderEntity.getUser().getId() != mUserService.getCurrentUser().getId()
                || (!orderEntity.getStatus().name().equalsIgnoreCase(OrderStatusEnum.PENDING.name())
                && !orderEntity.getStatus().name().equalsIgnoreCase(OrderStatusEnum.UNCONFIRMED.name()))) {
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
