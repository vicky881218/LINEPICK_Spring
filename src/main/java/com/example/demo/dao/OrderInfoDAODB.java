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

import com.example.demo.entity.OrderInfo; 

@Repository
public class OrderInfoDAODB implements OrderInfoDAO{
@Autowired
 private DataSource dataSource;
@Autowired
 JdbcTemplate jdbcTemplate;

 
public List<OrderInfo> findSellerOrderBuyerContent(int orderlist_id){
    List<OrderInfo> anOrderList = jdbcTemplate.query( 
      "select * from ((orderlist join buyer on orderlist.buyer_id = buyer.buyer_id) join order_item on orderlist.orderlist_id = order_item.orderlist_id) join product on order_item.product_id = product.product_id and orderlist.orderlist_id = ?", 
      new OrderInfoMapper(), orderlist_id);
      
    return anOrderList;
  }
  

public List<OrderInfo> findSellerOrder(String orderlist_status){
    List<OrderInfo> anOrderList = jdbcTemplate.query( 
      "select * from ((orderlist join buyer on orderlist.buyer_id = buyer.buyer_id) join order_item on orderlist.orderlist_id = order_item.orderlist_id) join product on order_item.product_id = product.product_id and orderlist.orderlist_status=? group by orderlist.orderlist_id", 
      new OrderInfoMapper(), orderlist_status);
      
    return anOrderList;
  }
  public List<OrderInfo> findBuyerOrder(String orderlist_status, String buyer_id){
    List<OrderInfo> anOrderList = jdbcTemplate.query( 
      "select * from ((orderlist join buyer on orderlist.buyer_id = buyer.buyer_id) join order_item on orderlist.orderlist_id = order_item.orderlist_id) join product on order_item.product_id = product.product_id and orderlist.orderlist_status=? where buyer.buyer_id = ? group by orderlist.orderlist_id", 
      new OrderInfoMapper(), orderlist_status, buyer_id);
      
    return anOrderList;
  }
public List<OrderInfo> findAllBuyerOrder(String buyer_id){
    List<OrderInfo> anOrderList = jdbcTemplate.query( 
      "select * from ((orderlist join buyer on orderlist.buyer_id = buyer.buyer_id) join order_item on orderlist.orderlist_id = order_item.orderlist_id) join product on order_item.product_id = product.product_id where buyer.buyer_id=? group by orderlist.orderlist_id", 
      new OrderInfoMapper(), buyer_id);
      
    return anOrderList;
  }

  

  private static final class OrderInfoMapper implements RowMapper<OrderInfo> {

    public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderInfo OrderInfo = new OrderInfo();
        OrderInfo.setOrderListId(rs.getInt("orderlist_id"));
        OrderInfo.setPayType(rs.getString("pay_type"));
        OrderInfo.setPayStatus(rs.getString("pay_status"));
        OrderInfo.setOrderListStatus(rs.getString("orderlist_status"));
        OrderInfo.setOrderListPayment(rs.getInt("orderlist_payment"));
        OrderInfo.setOrderDate(rs.getString("order_date"));
        OrderInfo.setPickmoneyUse(rs.getInt("pickmoney_use"));
        OrderInfo.setBuyerId(rs.getString("buyer_id"));
        OrderInfo.setBuyerName(rs.getString("buyer_name"));
        OrderInfo.setBuyerPhone(rs.getString("buyer_phone"));
        OrderInfo.setBuyerMail(rs.getString("buyer_mail"));
        OrderInfo.setBuyerAddress(rs.getString("buyer_address"));
        OrderInfo.setPickpoint(rs.getInt("pickpoint"));
        OrderInfo.setPickmoney(rs.getInt("pickmoney"));
        OrderInfo.setProductId(rs.getInt("product_id"));
        OrderInfo.setProductName(rs.getString("product_name"));
        OrderInfo.setProductDesc(rs.getString("product_desc"));
        OrderInfo.setProductPrice(rs.getInt("product_price"));
        OrderInfo.setProductStock(rs.getInt("product_stock"));
        OrderInfo.setProductPhoto(rs.getString("product_photo"));
        OrderInfo.setProductStyle(rs.getString("product_style"));
        OrderInfo.setOrderItemId(rs.getInt("order_item_id"));
        OrderInfo.setOrderItemQuantity(rs.getInt("order_item_quantity"));
        return OrderInfo;
    }
}
}
