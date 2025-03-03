package org.doit.order.application.usecase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.order.infraestructure.in.rest.dto.OrderResponse;
import org.doit.order.domain.model.OrderStatus;
import org.doit.order.domain.model.OrderEvent;
import org.doit.order.infraestructure.out.stream.OrderEventKafkaPublisher;
import org.doit.order.infraestructure.out.persistence.OrderEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ConfirmOrderUseCase {

    private final OrderEventRepository eventRepository;
    private final OrderEventKafkaPublisher orderPublisher;

    public OrderResponse confirmOrder(String orderId) {
        log.info("📌 Entering confirmOrder with request: {}", orderId);
        OrderEvent event = OrderEvent
                .builder()
                .orderId(orderId)
                .status(OrderStatus.CONFIRMED)
                .details("order confirmed")
                .eventTimestamp(LocalDateTime.now())
                .build();

        eventRepository.save(event);
        log.info("✅ Order saved successfully: {}", event);
        orderPublisher.send(event);
        log.info("✅ event sent successfully: {}", event);
        return new OrderResponse(orderId, OrderStatus.CONFIRMED);
    }
}
