package com.example.bekiashop.dto.consumes;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailConsumeDto implements Serializable {

    private Long id;

    private Integer quantity;

    private String description;
}
