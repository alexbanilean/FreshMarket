package com.unibuc.fresh_market.domain;

import com.unibuc.fresh_market.domain.security.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Range(min = 1, max = 5)
    private Integer rating;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
}
