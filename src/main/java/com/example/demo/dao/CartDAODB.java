package com.example.demo.dao;
//import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Cart; 

@Repository
public class CartDAODB implements CartDAO{

    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Cart findOne(int buyer_id) {
      Cart aCart = jdbcTemplate.queryForObject(
              "select * where buyer_id = ?",
              new CartMapper(), buyer_id);
              return aCart;
   }
    public List<Cart> findCartAllProduct(String buyer_id) {
        return this.jdbcTemplate.query( "select * from cart where buyer_id=?", 
         new CartMapper(), buyer_id);
      }
    public List<Cart> findCartAllProductByCartId(int cart_id) {
        return this.jdbcTemplate.query( "select * from cart where cart_id=?", 
         new CartMapper(), cart_id);
      }

    private static final class CartMapper implements RowMapper<Cart> {

        public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cart Cart = new Cart();
            Cart.setCartId(rs.getInt("cart_id"));
            Cart.setBuyerId(rs.getString("buyer_id"));
            Cart.setProductId(rs.getInt("product_id"));
            Cart.setQuantity(rs.getInt("quantity"));
            Cart.setChecked(rs.getString("checked"));
            return Cart;
        }
    }
   
    public int insert(Cart Cart) throws SQLException{
     return jdbcTemplate.update(
       "insert into Cart (cart_id, buyer_id, product_id, quantity,checked) values(?,?,?,?,?)",
       Cart.getCartId(),Cart.getBuyerId(), Cart.getProductId(),Cart.getQuantity(),Cart.getChecked());
    }
    
    public int update(Cart Cart) {
     return jdbcTemplate.update(
       "update Cart set buyer_id=?, product_id=?, quantity=?,checked=? where cart_id =?",
       Cart.getBuyerId(), Cart.getProductId(),Cart.getQuantity(),Cart.getChecked(),Cart.getCartId());
    }
   
    public int delete(int cart_id) {
     return jdbcTemplate.update(
       "delete from Cart where cart_id =?", cart_id);
    }
}
