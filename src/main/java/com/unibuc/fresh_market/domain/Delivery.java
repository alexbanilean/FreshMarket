package com.unibuc.fresh_market.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 30)
    private String deliveryStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
