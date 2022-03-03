package click.benshop.bebenshop.entities;

import click.benshop.bebenshop.bases.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class DeviceEntity extends BaseEntity {

    private String userAgent;

    @Column(columnDefinition = "text")
    private String accessToken;

    @Column(columnDefinition = "text")
    private String refreshToken;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id")
    private UserEntity user;
}
