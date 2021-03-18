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

import com.example.demo.entity.Buyer; 

@Repository
public class BuyerDAODB implements BuyerDAO {

  
 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

 /*
 @Bean
public JdbcTemplate getJdbcTemplate() {
  return new JdbcTemplate(dataSource);
}
*/

//jdbcTemplate  
 public Buyer findOne(String buyer_id) {
   try {
   Connection connection = dataSource.getConnection();
   }
   catch (Exception e){
     System.out.println("Error in findOne:"+e);
   }
   Buyer aBuyer = jdbcTemplate.queryForObject( 
    "select buyer_id,buyer_name, buyer_phone,buyer_mail,buyer_address, pickpoint,pickmoney from Buyer where buyer_id = ?", 
    new BuyerMapper(), buyer_id);
  return aBuyer;
    /*
     return this.jdbcTemplate.queryForObject( 
      "select buyer_id,buyer_name, buyer_phone,buyer_mail,buyer_address, pickpoint,pickmoney from Buyer where buyer_id = ?", 
      new BuyerMapper(),buyer_id); */
 }

 public List<Buyer> findAll() {
     return this.jdbcTemplate.query( "select buyer_id,buyer_name, buyer_phone,buyer_mail,buyer_address, pickpoint,pickmoney from buyer", 
      new BuyerMapper());
 }

 private static final class BuyerMapper implements RowMapper<Buyer> {

     public Buyer mapRow(ResultSet rs, int rowNum) throws SQLException {
         Buyer Buyer = new Buyer();
         Buyer.setBuyerId(rs.getString("buyer_id"));
         Buyer.setBuyerName(rs.getString("buyer_name"));
         Buyer.setBuyerPhone(rs.getString("buyer_phone"));
         Buyer.setBuyerMail(rs.getString("buyer_mail"));
         Buyer.setBuyerAddress(rs.getString("buyer_address"));
         Buyer.setPickpoint(rs.getInt("pickpoint"));
         Buyer.setPickmoney(rs.getInt("pickmoney"));
         return Buyer;
     }
 }

 public int insert(Buyer Buyer) throws SQLException{
  return jdbcTemplate.update(
    "insert into Buyer (buyer_id,buyer_name, buyer_phone,buyer_mail,buyer_address, pickpoint,pickmoney) values(?,?,?,?,?,?,?)",
    Buyer.getBuyerId(),Buyer.getBuyerName(), Buyer.getBuyerPhone(),Buyer.getBuyerMail(),
    Buyer.getBuyerAddress(), Buyer.getPickpoint(), Buyer.getPickmoney());
 }
 
 public int update(Buyer Buyer) {
  return jdbcTemplate.update(
    "update Buyer set buyer_name=?, buyer_phone=?,buyer_mail=?,buyer_address=?, pickpoint=?,pickmone=? where buyer_id =?",
    Buyer.getBuyerName(), Buyer.getBuyerPhone(),Buyer.getBuyerMail(),
    Buyer.getBuyerAddress(), Buyer.getPickpoint(), Buyer.getPickmoney(),Buyer.getBuyerId());
 }

 public int delete(String buyer_id) {
  return jdbcTemplate.update(
    "delete from Buyer where buyer_id =?", buyer_id);
 }

 
}