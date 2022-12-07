package ru.hehnev.tacocloud.repository;

import ru.hehnev.tacocloud.models.taco.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);
}
