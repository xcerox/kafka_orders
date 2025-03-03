package org.doit.order.infraestructure.in.rest.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderRequest {
    private String name;
    private Integer qty;
    private Double price;
    private String userId;
}
