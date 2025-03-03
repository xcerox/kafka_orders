package org.doit.order.infraestructure.in.rest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.order.application.usecase.PlaceOrderUseCase;
import org.doit.order.infraestructure.in.rest.dto.OrderRequest;
import org.doit.order.infraestructure.in.rest.dto.OrderResponse;
import org.doit.order.application.usecase.ConfirmOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final PlaceOrderUseCase placeOrderUseCase;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("🎯 Entering placeOrder with request: {}", orderRequest);
        try {
            OrderResponse orderResponse = placeOrderUseCase.placeOrder(orderRequest);
            log.info("✅ Order placed successfully: {}", orderResponse);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("❌ Error placing order", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<OrderResponse> confirmOrder(@PathVariable String orderId) {
        try {
            OrderResponse orderResponse = confirmOrderUseCase.confirmOrder(orderId);
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
