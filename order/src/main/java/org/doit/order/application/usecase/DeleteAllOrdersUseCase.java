package org.doit.order.application.usecase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.order.domain.model.OrderEvent;
import org.doit.order.domain.model.OrderStatus;
import org.doit.order.infraestructure.in.rest.dto.OrderResponse;
import org.doit.order.infraestructure.out.persistence.OrderEventRepository;
import org.doit.order.infraestructure.out.stream.OrderEventKafkaPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class DeleteAllOrdersUseCase {

    private final OrderEventRepository eventRepository;

    public void deleteAll() {
        log.info("📌 Entering deleteAll with request");
        eventRepository.deleteAll();
    }
}
