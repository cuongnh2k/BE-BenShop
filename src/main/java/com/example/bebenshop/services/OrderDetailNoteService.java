package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailNoteProduceDto;

public interface OrderDetailNoteService {

    OrderDetailNoteProduceDto editOrderDetailNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto);
}
