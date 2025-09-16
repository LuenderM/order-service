package com.luender.order.controller;

import com.luender.order.model.Order;
import com.luender.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping     // vai listar todos os pedidos
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders); // HTTP 200
    }

    // GET /orders/id -> busca pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok) // HTTP 200 se encontrado
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // HTTP 404 se não encontrado, caso digite id inválido!
    }

    // POST /orders -> cria novo pedido
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder); // HTTP 201
    }

    // PUT /orders/id -> atualiza pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Optional<Order> existingOrder = orderService.getOrderById(id);

        if (existingOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // HTTP 404
        }

        updatedOrder.setId(id); // garante que estamos atualizando o certo
        Order savedOrder = orderService.saveOrder(updatedOrder);
        return ResponseEntity.ok(savedOrder); // HTTP 200
    }

    // DELETE /orders/id -> deleta pedido por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> existingOrder = orderService.getOrderById(id);

        if (existingOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // HTTP 404
        }

        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
