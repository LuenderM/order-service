package com.luender.order.service;

import com.luender.order.DAO.OrderDAO;
import com.luender.order.model.Order;
import com.luender.order.model.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Page<Order> getAllOrders(Pageable pageable) {                 // buscar todos os pedidos
        return orderDAO.findAll(pageable);
    }

    public Optional<Order> getOrderById(Long id) {      // buscar por id
        return orderDAO.findById(id);
    }

    public void deleteOrder(Long id) {                  // deleta um pedido com número do id
        orderDAO.deleteById(id);
    }

    public Order saveOrder(Order order) {               // criar ou atualizar pedido
        // calcula o totalAmount
        BigDecimal total = BigDecimal.ZERO;

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                BigDecimal subtotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(subtotal);

                // garante que o item conhece a order para evitar erro de integridade
                item.setOrder(order);
            }
        }

        order.setTotalAmount(total);

        // salva no banco
        return orderDAO.save(order);
    }

}
