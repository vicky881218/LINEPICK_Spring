package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.OrderList;

import org.springframework.stereotype.Repository;



@Repository
public interface OrderListDAO {
    public List<OrderList> findAll();
    public OrderList findOne(String buyer_id);
}
