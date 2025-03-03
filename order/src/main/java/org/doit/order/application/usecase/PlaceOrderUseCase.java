package org.doit.order.application.usecase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.order.infraestructure.in.rest.dto.OrderRequest;
import org.doit.order.infraestructure.in.rest.dto.OrderResponse;
import org.doit.order.domain.model.OrderStatus;
import org.doit.order.domain.model.OrderEvent;
import org.doit.order.infraestructure.out.stream.OrderEventKafkaPublisher;
import org.doit.order.infraestructure.out.persistence.OrderEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PlaceOrderUseCase {

    private final OrderEventRepository eventRepository;
    private final OrderEventKafkaPublisher orderPublisher;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        log.info("🎯 Entering placeOrder with request: {}", orderRequest);
        String orderId = UUID.randomUUID().toString().split("-")[0];

        OrderEvent event = OrderEvent
                .builder()
                .orderId(orderId)
                .status(OrderStatus.CREATED)
                .details("order created")
                .eventTimestamp(LocalDateTime.now())
                .build();

        eventRepository.save(event);
        log.info("✅ Order saved successfully: {}", event);
        orderPublisher.send(event);
        log.info("✅ Order sent successfully: {}", event);
        return new OrderResponse(orderId, OrderStatus.CREATED);
    }
}
