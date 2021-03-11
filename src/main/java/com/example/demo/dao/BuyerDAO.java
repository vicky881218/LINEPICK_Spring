package com.example.demo.dao;

import com.example.demo.entity.Buyer;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface BuyerDAO {
    public int insert(Buyer buyer) throws SQLException;
    public List<Buyer> findAll();
    public Buyer findOne(String buyer_id);
    public int update(Buyer buyer);
    public int delete(String buyer_id);
}
