package org.doit.shipping.application.usecase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.shipping.domain.model.OrderEvent;
import org.doit.shipping.domain.model.OrderStatus;
import org.doit.shipping.infraestructure.out.repository.OrderEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class NotifyOrderStatusUpdateUseCase {

    private OrderEventRepository orderRepository;

    public void shipOrder(String orderId) {
        log.info("📌 Entering shipOrder with request: {}", orderId);

        OrderEvent event = OrderEvent
                .builder()
                .orderId(orderId)
                .status(OrderStatus.SHIPPED)
                .details("Order Shipped successfully")
                .eventTimestamp(LocalDateTime.now())
                .build();

        orderRepository.save(event);
        log.info("✅ Order event updated and saved successfully: {}", event);
    }

    public void deliverOrder(String orderId) {
        OrderEvent event = OrderEvent
                .builder()
                .orderId(orderId)
                .status(OrderStatus.DELIVERED)
                .details("Order delivered successfull")
                .eventTimestamp(LocalDateTime.now())
                .build();

        orderRepository.save(event);
    }
}
