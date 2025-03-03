package org.doit.shipping.infraestructure.out.repository;

import org.doit.shipping.domain.model.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent, String> { }
