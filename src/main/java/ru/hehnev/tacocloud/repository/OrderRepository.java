package ru.hehnev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hehnev.tacocloud.models.taco.TacoOrder;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryZip(String deliveryZip);
}
