package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.OrderList;

import org.springframework.stereotype.Repository;



@Repository
public interface OrderListDAO {
    public List<OrderList> findAll();
    public List<OrderList> findAllMyOrderList(String orderlist_status, String buyer_id);
    public List<OrderList> findAllMyOrderList1(String orderlist_status, String buyer_id);
    public List<OrderList> findAllMyOrderList2(String orderlist_status, String buyer_id);
    public OrderList findOne(String buyer_id);
    public OrderList findByOrderStatus(String orderlist_status);
    public OrderList findByOrderStatus1(String orderlist_status);
    public OrderList findByOrderStatus2(String orderlist_status);
}
