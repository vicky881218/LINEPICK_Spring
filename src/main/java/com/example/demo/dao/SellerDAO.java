package com.example.demo.dao;

import com.example.demo.entity.Seller;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface SellerDAO {
    public int insert(Seller seller) throws SQLException;
    public List<Seller> findAll();
    public Seller findOne(int seller_id);
    public int update(Seller seller);
    public int delete(int seller_id);

}
