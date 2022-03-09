package com.example.bebenshop.bases;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class BaseProduceDto<ID> implements Serializable {

    @JsonProperty("id")
    private ID id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updatedDate;
}