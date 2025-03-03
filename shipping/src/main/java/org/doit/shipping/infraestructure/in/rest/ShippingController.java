package org.doit.shipping.infraestructure.in.rest;

import lombok.AllArgsConstructor;
import org.doit.shipping.application.usecase.NotifyOrderStatusUpdateUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping")
@AllArgsConstructor
public class ShippingController {

    private NotifyOrderStatusUpdateUseCase notifyOrderStatusUpdateUseCase;

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<String> shipOrder(@PathVariable String orderId) {
        notifyOrderStatusUpdateUseCase.shipOrder(orderId);
        return ResponseEntity.ok("Order shipped successfully.");
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<String> deliverOrder(@PathVariable String orderId) {
        notifyOrderStatusUpdateUseCase.deliverOrder(orderId);
        return ResponseEntity.ok("Order delivered successfully.");
    }
}