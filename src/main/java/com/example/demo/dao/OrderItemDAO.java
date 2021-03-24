package com.example.demo.dao;
import java.util.List;

import com.example.demo.entity.OrderItem;

import org.springframework.stereotype.Repository;

public interface OrderItemDAO {
    public OrderItem findOne(int order_item_id);
    public List<OrderItem> findAll();
    public List<OrderItem> findProductId(int orderlist_id);
    
}
