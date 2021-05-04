package com.example.demo.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderList; 


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
    
 }
 public OrderList findByOrderStatus(String orderlist_status) {
  orderlist_status = "已完成";
  OrderList anOrderList = jdbcTemplate.queryForObject( 
    "select * from orderlist where orderlist_status = ? group by orderlist_status", 
    new OrderListMapper(), orderlist_status);
  return anOrderList;
}
public OrderList findByOrderStatus1(String orderlist_status) {
 orderlist_status = "未出貨";
  OrderList anOrderList = jdbcTemplate.queryForObject( 
    "select * from orderlist where orderlist_status=? group by orderlist_status", 
    new OrderListMapper(), orderlist_status);
    
  return anOrderList;
   
}
public OrderList findByOrderStatus2(String orderlist_status) {
  orderlist_status = "運送中";
   OrderList anOrderList = jdbcTemplate.queryForObject( 
     "select * from orderlist where orderlist_status=? group by orderlist_status", 
     new OrderListMapper(), orderlist_status);
     
   return anOrderList;
    
 }
 public List<OrderList> findAll() {
    return this.jdbcTemplate.query( "select * from orderlist", 
     new OrderListMapper());
}

public List<OrderList> findAllMyOrderList(String orderlist_status, String buyer_id) {
  orderlist_status = "已完成";
  return this.jdbcTemplate.query( "select * from orderlist where orderlist_status=? and buyer_id=? order by orderlist_id DESC", 
   new OrderListMapper(), orderlist_status, buyer_id);
}

public List<OrderList> findAllMyOrderList1(String orderlist_status, String buyer_id) {
  orderlist_status = "未出貨";
  return this.jdbcTemplate.query( "select * from orderlist where orderlist_status=? and buyer_id=? order by orderlist_id DESC", 
   new OrderListMapper(),orderlist_status, buyer_id);
}
public List<OrderList> findAllMyOrderList2(String orderlist_status, String buyer_id) {
  orderlist_status = "運送中";
  return this.jdbcTemplate.query( "select * from orderlist where orderlist_status=? and buyer_id=? order by orderlist_id DESC", 
   new OrderListMapper(),orderlist_status, buyer_id);
}

public List<OrderList> findTheLastOrderlistId(String buyer_id) {
  return this.jdbcTemplate.query( "select * from orderlist where buyer_id=?", 
   new OrderListMapper(), buyer_id);
}

public OrderList findTheLastRecordOfOrderlist(String buyer_id,int totalLength) {
  return this.jdbcTemplate.queryForObject( "select * from orderlist where buyer_id=? and orderlist_id=?", 
   new OrderListMapper(), buyer_id,totalLength);
}

public int inserts(OrderList orderList){
  KeyHolder keyHolder = new GeneratedKeyHolder();
  String sql = "insert into orderlist (pay_type, pay_status, orderlist_status, orderlist_payment, order_date, pickmoney_use, buyer_id) values(?,?,?,?,?,?,?)";  

  jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection
        .prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,orderList.getPayType());
        ps.setString(2,orderList.getPayStatus());
        ps.setString(3,orderList.getOrderListStatus());
        ps.setInt(4,orderList.getOrderListPayment());
        ps.setString(5,orderList.getOrderDate());
        ps.setInt(6,orderList.getPickmoneyUse());
        ps.setString(7,orderList.getBuyerId());
        return ps;
      }, keyHolder);
      Number key = keyHolder.getKey();
      return key.intValue(); 

}
 



 private static final class OrderListMapper implements RowMapper<OrderList> {

     public OrderList mapRow(ResultSet rs, int rowNum) throws SQLException {
         OrderList OrderList = new OrderList();
         OrderList.setOrderListId(rs.getInt("orderlist_id"));
         OrderList.setPayType(rs.getString("pay_type"));
         OrderList.setPayStatus(rs.getString("pay_status"));
         OrderList.setOrderListStatus(rs.getString("orderlist_status"));
         OrderList.setOrderListPayment(rs.getInt("orderlist_payment"));
         OrderList.setOrderDate(rs.getString("order_date"));
         OrderList.setPickmoneyUse(rs.getInt("pickmoney_use"));
         OrderList.setBuyerId(rs.getString("buyer_id"));
        
         return OrderList;
     }
 }

}
