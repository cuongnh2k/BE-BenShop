package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.OrderDetailNoteEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailNoteConsumeDto implements Serializable {

    private String content;

    private Long orderDetailId;

    public OrderDetailNoteEntity toOrderNoteEntity() {
        return OrderDetailNoteEntity.builder()
                .content(content)
                .build();
    }
}
