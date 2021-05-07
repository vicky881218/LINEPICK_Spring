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

import com.example.demo.entity.Seller; 

@Repository
public class SellerDAODB implements SellerDAO {
 
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
 public Seller findOne(int seller_id) {
   try {
   Connection connection = dataSource.getConnection();
   }
   catch (Exception e){
     System.out.println("Error in findOne:"+e);
   }
   Seller aSeller = jdbcTemplate.queryForObject( 
    "select seller_id,seller_account,seller_password,seller_mail,seller_phone,market_name,market_desc from seller where seller_id = ?", 
    new SellerMapper(), seller_id);
  return aSeller;
 }

 public List<Seller> findAll() {
     return this.jdbcTemplate.query( "select seller_id,seller_account,seller_password,seller_mail,seller_phone,market_name,market_desc from seller", 
      new SellerMapper());
 }


 public Seller login(String account,String password) {
   
    Seller thisSeller = jdbcTemplate.queryForObject( 
     "select seller_id,seller_account,seller_password,seller_mail,seller_phone,market_name,market_desc from seller where seller_account like ? and seller_password like ?", 
     new SellerMapper(), account,password);
   return thisSeller;
  }

 private static final class SellerMapper implements RowMapper<Seller> {

     public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seller seller = new Seller();
        seller.setSellerId(rs.getInt("seller_id"));
        seller.setSellerAccount(rs.getString("seller_account"));
        seller.setSellerPassword(rs.getString("seller_password"));
        seller.setSellerMail(rs.getString("seller_mail"));
        seller.setSellerPhone(rs.getString("seller_phone"));
        seller.setMarketName(rs.getString("market_name"));
        seller.setMarketDesc(rs.getString("market_desc"));
         return seller;
     }
 }

 public int insert(Seller seller) throws SQLException{
   
  return jdbcTemplate.update(
    "insert into seller (seller_id,seller_account,seller_password,seller_mail,seller_phone,market_name,market_desc) values(?,?,?,?,?,?,?)",
    seller.getSellerId(),seller.getSellerAccount(), seller.getSellerPassword(),seller.getSellerMail(),
    seller.getSellerPhone(),seller.getMarketName(), seller.getMarketDesc());
 }
 
 public int update(Seller seller) {
  return jdbcTemplate.update(
    "update seller set seller_account=?,seller_password=?,seller_mail=?,seller_phone=?,market_name=?,market_desc=? where seller_id =?",
    seller.getSellerAccount(), seller.getSellerPassword(), seller.getSellerMail(),
    seller.getSellerPhone(), seller.getMarketName(), seller.getMarketDesc(), seller.getSellerId());
 }

 public int delete(int seller_id) {
  return jdbcTemplate.update(
    "delete from seller where seller_id =?", seller_id);
 }

}
