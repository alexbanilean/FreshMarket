package com.unibuc.fresh_market.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
}
