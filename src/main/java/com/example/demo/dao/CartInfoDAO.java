package com.example.demo.dao;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.entity.CartInfo;

public interface CartInfoDAO {
    public List<CartInfo> findJoinCartAllProduct(String buyer_id);
    public List<CartInfo> findCheckedCart(String buyer_id);
}
