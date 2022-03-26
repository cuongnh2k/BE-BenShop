package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailNoteProduceDto;
import com.example.bebenshop.entities.OrderDetailNoteEntity;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.OrderDetailNoteMapper;
import com.example.bebenshop.repository.OrderDetailNoteRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.services.OrderDetailNoteService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailNoteServiceImpl implements OrderDetailNoteService {
    private final OrderDetailNoteRepository mOrderNoteRepository;
    private final OrderRepository mOrderRepository;
    private final OrderDetailNoteMapper mOrderNoteMapper;
    private final UserService mUserService;

    @Override
    public void deleteOderNoteById(Long id) {
        OrderDetailNoteEntity orderNoteEntity = mOrderNoteRepository.findById(id).orElse(null);
        if (orderNoteEntity == null) {
            throw new BadRequestException("Order note does not  exits");
        }
        if (orderNoteEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
//        mOrderNoteRepository.deleteOrderNoteById(id);
    }

//    public OrderDetailNoteProduceDto addOrderNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto) {
//
//        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);
//        if (orderEntity == null) {
//            throw new BadRequestException("Order does not  exits");
//        }
//        if (mUserService.getUserName().equals(orderEntity.getCreatedBy())
//                || (orderEntity.getStatus().equals(OrderStatusEnum.CANCELED))
//                || (orderEntity.getStatus().equals(OrderStatusEnum.COMPLETED))
//                || (orderEntity.getStatus().equals(OrderStatusEnum.RESOLVED))) {
//            throw new ForbiddenException("Forbidden");
//        }
//        OrderDetailNoteEntity orderNoteEntity = orderNoteConsumeDto.toOrderNoteEntity();
//        orderNoteEntity.setOrder(orderEntity);
//        mOrderNoteRepository.save(orderNoteEntity);
//        return mOrderNoteMapper.toOrderNoteProduceDto(orderNoteEntity);
//    }

    @Override
    public OrderDetailNoteProduceDto editOrderNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto) {
        OrderDetailNoteEntity orderNoteEntity = mOrderNoteRepository.findById(id).orElse(null);
        if (orderNoteEntity == null) {
            throw new BadRequestException("Order note does not  exits");
        }
        if (orderNoteEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        orderNoteEntity.setContent(orderNoteConsumeDto.toOrderNoteEntity().getContent());
        return mOrderNoteMapper.toOrderNoteProduceDto(mOrderNoteRepository.save(orderNoteEntity));
    }
}
