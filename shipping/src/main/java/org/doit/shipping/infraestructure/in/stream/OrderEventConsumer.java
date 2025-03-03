package org.doit.shipping.infraestructure.in.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.shipping.application.usecase.NotifyOrderStatusUpdateUseCase;
import org.doit.shipping.domain.model.OrderEvent;
import org.doit.shipping.domain.model.OrderStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderEventConsumer {

    private NotifyOrderStatusUpdateUseCase notifyOrderStatusUpdateUseCase;
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.event.topicName}", groupId = "${spring.kafka.consumer.group-id}")
    public void processOrderEvent(String stringEvent) throws JsonProcessingException {
        log.info("📌 Entering processOrderEvent with request: {}", stringEvent);

        OrderEvent orderEvent = objectMapper.readValue(stringEvent, OrderEvent.class);
        log.info("✅ string converted {} successfully", orderEvent);

        if (OrderStatus.CONFIRMED.equals(orderEvent.getStatus())) {
            log.info("✅ Order has confirmed status");
            notifyOrderStatusUpdateUseCase.shipOrder(orderEvent.getOrderId());
        }
    }
}
