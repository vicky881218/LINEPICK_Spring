package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.CartInfo; 

@Repository
public class CartInfoDAODB implements CartInfoDAO{
    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CartInfo> findJoinCartAllProduct(String buyer_id) {
        return this.jdbcTemplate.query( "select * from cart join product on cart.product_id=product.product_id and buyer_id=?", 
         new CartInfoMapper(),buyer_id);
      }

    private static final class CartInfoMapper implements RowMapper<CartInfo> {

        public CartInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartInfo CartInfo = new CartInfo();
            CartInfo.setCartId(rs.getInt("cart_id"));
            CartInfo.setBuyerId(rs.getString("buyer_id"));
            CartInfo.setProductId(rs.getInt("product_id"));
            CartInfo.setQuantity(rs.getInt("quantity"));
            CartInfo.setProductName(rs.getString("product_name"));
            CartInfo.setProductDesc(rs.getString("product_desc"));
            CartInfo.setProductPrice(rs.getInt("product_price"));
            CartInfo.setProductStock(rs.getInt("product_stock"));
            CartInfo.setProductPhoto(rs.getString("product_photo"));
            CartInfo.setProductStyle(rs.getString("product_style"));
            CartInfo.setDiscount(rs.getInt("discount"));
            return CartInfo;
        }
    }
}
