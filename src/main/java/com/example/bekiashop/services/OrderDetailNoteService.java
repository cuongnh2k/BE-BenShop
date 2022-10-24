package com.example.bekiashop.services;

import com.example.bekiashop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bekiashop.dto.produces.OrderDetailNoteProduceDto;

public interface OrderDetailNoteService {

    OrderDetailNoteProduceDto editOrderDetailNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto);

    OrderDetailNoteProduceDto addOrderDetailNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto);

    void deleteOrderDetailNote(Long id);
}
