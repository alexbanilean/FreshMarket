package com.unibuc.fresh_market.domain;

import jakarta.persistence.*;
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

    private String deliveryStatus;
    private Date deliveryDate;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
