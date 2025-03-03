package org.doit.order.infraestructure.in.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.doit.order.application.usecase.SaveOrderEventBackupUseCase;
import org.doit.order.domain.model.OrderEvent;
import org.doit.order.infraestructure.in.stream.config.KafkaConsumerConfig;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;


@Slf4j
@Service
@AllArgsConstructor
public class OrderEventBackupConsumer {

    private final KafkaConsumerConfig consumerConfig;
    private final ObjectMapper objectMapper;
    private final SaveOrderEventBackupUseCase saveOrderEventBackupUseCase;


    public Boolean runBackup() {
        Boolean isSuccess = Boolean.TRUE;
        log.info("🎯 Entering runBackup");

        try (KafkaConsumer<String, String> consumer = consumerConfig.buildConsumer()) {
            log.info("✅ starting restore backup");
            consumer.subscribe(consumerConfig.getOrderEventTopic());
            log.info("✅ connecting consumer to topic: {}", consumerConfig.getTopicName());

            consumer.poll(Duration.ofMillis(100));
            consumer.assignment().forEach(partition -> consumer.seekToBeginning(Collections.singleton(partition)));
            log.info("✅ setting consumer get data from begging by partition");

            var hasMoreRecords = true;
            while (hasMoreRecords) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
                log.info("✅ pulling records, count: {}", records.count());
                if (records.isEmpty()) {
                    log.info("🔚 no more events, breaking process");
                    hasMoreRecords = false;
                } else {
                    records.forEach(record -> {
                        log.info("✅ taking record: {}", record);
                        OrderEvent orderEvent = null;
                        try {
                            orderEvent = objectMapper.readValue(record.value(), OrderEvent.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        log.info("✅ transforming to object using jsonmapper: {}", orderEvent);
                        saveOrderEventBackupUseCase.saveOrderBackup(orderEvent);
                    });
                }

                log.info("✅ commiting to sync with Kafka");
                consumer.commitSync();
            }

        } catch (Exception e) {
            isSuccess = Boolean.FALSE;
            log.error("❌ Error on deserializing event: {}", e.getMessage());
        }

        log.info("✅ run successfully : {}", isSuccess);
        return isSuccess;
    }
}
