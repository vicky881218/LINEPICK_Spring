package com.example.demo.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderItem;

@Repository
public class OrderItemDAODB implements OrderItemDAO{
@Autowired
 private DataSource dataSource;
@Autowired
 JdbcTemplate jdbcTemplate;


 public OrderItem findOne(int order_item_id) {
    OrderItem aOrderItem = jdbcTemplate.queryForObject(
            "select order_item_id, order_item_quantity, product_id, orderlist_id from order_item where order_item_id = ?",
            new OrderItemMapper(), order_item_id);
            return aOrderItem;
 }

    public List<OrderItem> findAll() {
        return this.jdbcTemplate.query(
                "select order_item_id, order_item_quantity, product_id, orderlist_id from order_item",
                new OrderItemMapper());
    }
    public List<OrderItem> findProductId(int orderlist_id) {
        return this.jdbcTemplate.query( "select * from order_item where orderlist_id=?", 
         new OrderItemMapper(), orderlist_id);
      }
    public OrderItem findOneOrderItemQuantity(int orderlist_id) {
        OrderItem aOrderItem = jdbcTemplate.queryForObject(
                "select order_item_id, order_item_quantity, product_id, orderlist_id from order_item where orderlist_id = ?",
                new OrderItemMapper(), orderlist_id);
                return aOrderItem;
     }
   
      public int insert(OrderItem OrderItem){ 
        return jdbcTemplate.update(
          "insert into order_item (order_item_id, order_item_quantity, product_id, orderlist_id) values(?,?,?,?)",
          OrderItem.getOrderItemId(),OrderItem.getOrderItemQuantity(),OrderItem.getProductId(),OrderItem.getOrderListId());
       }    

    private static final class OrderItemMapper implements RowMapper<OrderItem> {

        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem OrderItem = new OrderItem();
            OrderItem.setOrderItemId(rs.getInt("order_item_id"));
            OrderItem.setOrderItemQuantity(rs.getInt("order_item_quantity"));
            OrderItem.setProductId(rs.getInt("product_id"));
            OrderItem.setOrderListId(rs.getInt("orderlist_id"));
            return OrderItem;
   
        }
    }
   
    
}