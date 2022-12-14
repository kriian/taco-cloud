package ru.hehnev.tacocloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.hehnev.tacocloud.models.taco.TacoOrder;
import ru.hehnev.tacocloud.repository.JdbcOrderRepository;
import ru.hehnev.tacocloud.repository.OrderRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepository;
    private JdbcOrderRepository jdbcOrderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, JdbcOrderRepository jdbcOrderRepository) {
        this.orderRepository = orderRepository;
        this.jdbcOrderRepository = jdbcOrderRepository;
    }

    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(
            @Valid TacoOrder order,
            Errors errors,
            SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "orderForm";
        }
        jdbcOrderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
