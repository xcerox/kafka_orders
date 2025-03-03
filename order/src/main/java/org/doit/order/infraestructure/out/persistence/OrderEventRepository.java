package org.doit.order.infraestructure.out.persistence;

import org.doit.order.domain.model.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent, String> {

}
