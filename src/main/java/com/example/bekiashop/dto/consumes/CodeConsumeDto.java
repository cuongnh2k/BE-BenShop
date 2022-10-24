package com.example.bekiashop.dto.consumes;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeConsumeDto implements Serializable {

    private String code;
}
