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

import com.example.demo.entity.Product; 

@Repository
public class ProductDAODB implements ProductDAO{
    
@Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

 public Product findOne(int product_id) {

   Product aProduct = jdbcTemplate.queryForObject( 
    "select product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size from product where product_id = ?", 
    new ProductMapper(), product_id);
  return aProduct;
 }

 public List<Product> findAll() {
     return this.jdbcTemplate.query( "select product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size from product", 
      new ProductMapper());
 }

 public List<Product> findOneByName(String product_name) {
 return this.jdbcTemplate.query( 
  "select product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size from product where product_name = ? group by product_name", 
  new ProductMapper(), product_name);
}

 public List<Product> findOneTypeAllProduct(int product_id) {
  return this.jdbcTemplate.query( "select product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size from product where product_id=? group by product_name", 
   new ProductMapper(),product_id);
}

 public List<Product> findOneProductAllStyle(String product_name) {
  return this.jdbcTemplate.query( "select product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size from product where product_name=? group by product_style ", 
   new ProductMapper(),product_name);
}

public List<Product> findOneProductAllSize(String product_style) {
  return this.jdbcTemplate.query( "select product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size from product where product_style=?", 
   new ProductMapper(),product_style);
}


 private static final class ProductMapper implements RowMapper<Product> {

     public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
         Product Product = new Product();
         Product.setProductId(rs.getInt("product_id"));
         Product.setProductName(rs.getString("product_name"));
         Product.setProductDesc(rs.getString("product_desc"));
         Product.setProductPrice(rs.getInt("product_price"));
         Product.setProductStock(rs.getInt("product_stock"));
         Product.setProductPhoto(rs.getString("product_photo"));
         Product.setProductStyle(rs.getString("product_style"));
         Product.setProductSize(rs.getString("product_size"));
         return Product;

     }
 }

 public int insert(Product Product) throws SQLException{
  return jdbcTemplate.update(
    "insert into product (product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style,product_size) values(?,?,?,?,?,?,?,?)",
    Product.getProductId(),Product.getProductName(), Product.getProductDesc(),Product.getProductPrice(),
    Product.getProductStock(),Product.getProductPhoto(), Product.getProductStyle(),Product.getProductSize());
 }
 
 public int update(Product Product) {
  return jdbcTemplate.update(
    "update product set product_id=?,product_name=?, product_desc=?,product_price=?,product_stock=?,product_photo=?,product_style=?,product_size=? where product_id =?",
    Product.getProductId(),Product.getProductName(), Product.getProductDesc(),Product.getProductPrice(),
    Product.getProductStock(),Product.getProductPhoto(), Product.getProductStyle(),Product.getProductSize());
 }

 public int delete(String product_id) {
  return jdbcTemplate.update(
    "delete from product where product_id =?", product_id);
 }
}
