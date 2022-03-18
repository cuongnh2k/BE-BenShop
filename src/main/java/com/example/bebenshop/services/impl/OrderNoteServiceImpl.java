package com.example.bebenshop.services.impl;

import com.example.bebenshop.entities.OrderNoteEntity;
import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderNoteProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.OrderNoteEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.OrderNoteMapper;
import com.example.bebenshop.repository.OrderNoteRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.services.OrderNoteService;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderNoteServiceImpl implements OrderNoteService {
    private final OrderNoteRepository mOrderNoteRepository;
    private final OrderRepository mOrderRepository;
    private final OrderNoteMapper mOrderNoteMapper;
    private final UserService mUserService;
    private  final OrderService mOrderService;

    @Override
    public void deleteOderNoteById(Long id) {
        OrderNoteEntity orderNoteEntity = mOrderNoteRepository.findById(id).orElse(null);
        if (orderNoteEntity == null) {
            throw new BadRequestException("Id " + id + " not does exits ");
        }
        if (orderNoteEntity.getOrder().getUser().getId() != mUserService.getCurrentUser().getId()) {
            throw new ForbiddenException(" not authorithor delete order note");
        }
        mOrderNoteRepository.deleteOrderNoteById(id);
    }

    public OrderNoteProduceDto addOrderNote(Long id, OrderNoteConsumeDto orderNoteConsumeDto) {

        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);
        if (orderEntity == null) {
            throw new BadRequestException("No order exists: " + id);
        }
        if (mUserService.getCurrentUser().getId() != orderEntity.getUser().getId()
                || (orderEntity.getStatus().equals(OrderStatusEnum.CANCELED))
                || (orderEntity.getStatus().equals(OrderStatusEnum.COMPLETED))
                || (orderEntity.getStatus().equals(OrderStatusEnum.RESOLVED))) {
            throw new ForbiddenException("Forbidden");
        }
        OrderNoteEntity orderNoteEntity = orderNoteConsumeDto.toOrderNoteEntity();
        orderNoteEntity.setOrder(orderEntity);
        mOrderNoteRepository.save(orderNoteEntity);
        return mOrderNoteMapper.toOrderNoteProduceDto(orderNoteEntity);

    }

    @Override
    public OrderNoteProduceDto editOrderNote(Long id, OrderNoteConsumeDto orderNoteConsumeDto) {

        OrderNoteEntity orderNoteEntity = mOrderNoteRepository.findById(id).orElse(null);

        if (orderNoteEntity == null){
            throw new BadRequestException("Id " + id + "not does exists");
        }
        if(orderNoteEntity.getOrder().getUser().getId() != mUserService.getCurrentUser().getId() ){
            throw new ForbiddenException(" no edit access");
        }
        orderNoteEntity.setContent(orderNoteConsumeDto.toOrderNoteEntity().getContent());
    return mOrderNoteMapper.toOrderNoteProduceDto(mOrderNoteRepository.save(orderNoteEntity));
    }
}
