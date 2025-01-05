package com.unibuc.fresh_market.domain.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 30)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

}
