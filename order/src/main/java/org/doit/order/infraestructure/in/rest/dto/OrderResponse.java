package org.doit.order.infraestructure.in.rest.dto;

import lombok.*;
import org.doit.order.domain.model.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderResponse {
    private String orderId;
    private OrderStatus orderStatus;
}
