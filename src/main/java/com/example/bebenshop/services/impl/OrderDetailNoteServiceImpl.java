package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailNoteProduceDto;
import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.entities.OrderDetailNoteEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.OrderDetailNoteMapper;
import com.example.bebenshop.repository.OrderDetailNoteRepository;
import com.example.bebenshop.repository.OrderDetailRepository;
import com.example.bebenshop.services.OrderDetailNoteService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailNoteServiceImpl implements OrderDetailNoteService {

    private final OrderDetailNoteRepository mOrderDetailNoteRepository;
    private final OrderDetailNoteMapper mOrderDetailNoteMapper;
    private final UserService mUserService;
    private final OrderDetailRepository mOrderDetailRepository;

    @Override
    public OrderDetailNoteProduceDto editOrderDetailNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto) {
        OrderDetailNoteEntity orderDetailNoteEntity = mOrderDetailNoteRepository.findById(id).orElse(null);
        if (orderDetailNoteEntity == null) {
            throw new BadRequestException("Order note does not  exist");
        }
        if (!orderDetailNoteEntity.getCreatedBy().equals(mUserService.getUserName())
                || (!orderDetailNoteEntity.getOrderDetail().getOrder().getStatus().equals(OrderStatusEnum.PENDING)
                && !mUserService.isRoleAdmin())) {
            throw new ForbiddenException("Forbidden");
        }
        orderDetailNoteEntity.setContent(orderNoteConsumeDto.toOrderNoteEntity().getContent());
        return mOrderDetailNoteMapper.toOrderNoteProduceDto(mOrderDetailNoteRepository.save(orderDetailNoteEntity));
    }

    @Override
    public OrderDetailNoteProduceDto addOrderDetailNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto) {
        OrderDetailEntity orderDetailEntity = mOrderDetailRepository.findById(id).orElse(null);
        if (orderDetailEntity == null) {
            throw new BadRequestException("order detail does not exist");
        }
        return mOrderDetailNoteMapper.toOrderNoteProduceDto(mOrderDetailNoteRepository.save(OrderDetailNoteEntity.builder()
                .content(orderNoteConsumeDto.getContent())
                .orderDetail(orderDetailEntity)
                .build()));
    }

    @Override
    public void deleteOrderDetailNote(Long id) {
        OrderDetailNoteEntity orderDetailNoteEntity = mOrderDetailNoteRepository.findById(id).orElse(null);
        if (orderDetailNoteEntity == null) {
            throw new BadRequestException("order detail note does not exist");
        }
        if (!orderDetailNoteEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        mOrderDetailNoteRepository.deleteById(id);
    }
}
