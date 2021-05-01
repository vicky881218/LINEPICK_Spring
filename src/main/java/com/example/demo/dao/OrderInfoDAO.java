package com.example.demo.dao;

import com.example.demo.entity.OrderInfo;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface OrderInfoDAO {
    public List<OrderInfo> findSellerOrderBuyerContent(int orderlist_id);
    public List<OrderInfo> findSellerOrder(String orderlist_status);
}
