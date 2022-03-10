package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.entities.OrderNoteEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.OrderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderProduceDto extends BaseProduceDto<Long> {

    private OrderEnum status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserProduceDto user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderDetailProduceDto> orderDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderNoteProduceDto> orderNotes;
}
