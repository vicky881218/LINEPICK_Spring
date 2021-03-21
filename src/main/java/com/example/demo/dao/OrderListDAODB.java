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

import com.example.demo.entity.OrderList; 
import com.example.demo.entity.OrderItem;

@Repository
public class OrderListDAODB implements OrderListDAO{
@Autowired
 private DataSource dataSource;
@Autowired
 JdbcTemplate jdbcTemplate;

 public OrderList findOne(String buyer_id) {
  
   try {
   Connection connection = dataSource.getConnection();
   
   }
   catch (Exception e){
     System.out.println("Error in findOne:"+e);
   }
   OrderList anOrderList = jdbcTemplate.queryForObject( 
    "select * from orderlist where buyer_id = ?", 
    new OrderListMapper(), buyer_id);
    
  return anOrderList;
    /*
     return this.jdbcTemplate.queryForObject( 
      "select buyer_id,buyer_name, buyer_phone,buyer_mail,buyer_address, pickpoint,pickmoney from Buyer where buyer_id = ?", 
      new BuyerMapper(),buyer_id); */
 }
 public List<OrderList> findAll() {
    return this.jdbcTemplate.query( "select * from orderlist", 
     new OrderListMapper());
}

 private static final class OrderListMapper implements RowMapper<OrderList> {

     public OrderList mapRow(ResultSet rs, int rowNum) throws SQLException {
         OrderList OrderList = new OrderList();
         OrderList.setOrderListId(rs.getInt("orderlist_id"));
         OrderList.setPayType(rs.getString("pay_type"));
         OrderList.setPayStatus(rs.getString("pay_status"));
         OrderList.setOrderListStatus(rs.getString("orderlist_status"));
         OrderList.setOrderListPayment(rs.getInt("orderlist_payment"));
         OrderList.setBuyerId(rs.getString("buyer_id"));
        
         return OrderList;
     }
 }

}
