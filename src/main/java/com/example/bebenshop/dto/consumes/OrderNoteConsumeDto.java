package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.OrderNoteEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderNoteConsumeDto implements Serializable {
    private String content;
    private Long userId;
    public OrderNoteEntity toOrderNoteEntity(){
        return OrderNoteEntity.builder()
                .content(content)
                .build();
    }
}
