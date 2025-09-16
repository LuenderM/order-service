package com.luender.order.service;

import com.luender.order.DAO.OrderItemDAO;
import com.luender.order.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;

    public OrderItemService(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    public List<OrderItem> getAllItems() {              // lista todos os itens do order
        return orderItemDAO.findAll();
    }

    public Optional<OrderItem> getItemById(Long id) {       //lista um item pelo id
        return orderItemDAO.findById(id);
    }

    public OrderItem saveItem(OrderItem item) {             // cria ou atualiza um item
        return orderItemDAO.save(item);
    }

    public void deleteItem(Long id) {                       // deleta um item pelo id
        orderItemDAO.deleteById(id);
    }

}
