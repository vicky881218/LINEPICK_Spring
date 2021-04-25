package com.example.demo.dao;
import com.example.demo.entity.Cart;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;


public interface CartDAO {
    public List<Cart> findCartAllProduct(String buyer_id);
    public List<Cart> findCartAllProductByCartId(int cart_id);
    public Cart findOne(int buyer_id);
    public int insert(Cart cart) throws SQLException;
    public int update(Cart cart);
    public int delete(int cart_id);
}
