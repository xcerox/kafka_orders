package org.doit.order.infraestructure.out.stream;

import org.doit.order.domain.model.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventKafkaPublisher {

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private String topicName;

    OrderEventKafkaPublisher(KafkaTemplate<String, OrderEvent> kafkaTemplate,
                             @Value("${order.event.topicName}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }


    public void send(OrderEvent event){
        kafkaTemplate.send(topicName, event);
    }
}
