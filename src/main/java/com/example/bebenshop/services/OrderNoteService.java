package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderNoteProduceDto;

public interface OrderNoteService {

    void deleteOderNoteById(Long id);

    OrderNoteProduceDto addOrderNote(Long id, OrderNoteConsumeDto orderNoteConsumeDto);

}
