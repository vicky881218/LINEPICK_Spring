package com.example.demo.dao;

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

import com.example.demo.entity.Product; 

@Repository
public class ProductDAODB implements ProductDAO{
    
@Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

 public Product findOne(int product_id) {

   Product aProduct = jdbcTemplate.queryForObject( 
    "select * from product where product_id = ?", 
    new ProductMapper(), product_id);
  return aProduct;
 }
 public Product findOrderInformationProductId2(String product_style) {
  Product aProduct = jdbcTemplate.queryForObject( 
   "select * from product where product_style=?", 
   new ProductMapper(), product_style);
 return aProduct;
}

public Product findOrderInformationProductId(String product_name, String product_style) {
  Product aProduct = jdbcTemplate.queryForObject( 
   "select * from product where product_name = ? and product_style=?", 
   new ProductMapper(), product_name, product_style);
 return aProduct;
}

 public List<Product> findAll() {
     return this.jdbcTemplate.query( "select * from product group by product_name", 
      new ProductMapper());
 }

 public List<Product> findProductAll() {
  return this.jdbcTemplate.query( "select * from product", 
   new ProductMapper());
}

 public List<Product> findOrderProduct(int product_id) {
  return this.jdbcTemplate.query( "select * from product where product_id=?", 
   new ProductMapper(), product_id);
}
public List<Product> findOneByName(String product_name) {
  return this.jdbcTemplate.query( 
   "select * from product where product_name = ? group by product_name", 
   new ProductMapper(), product_name);
 }
 
  public Product findOneTypeAllProduct(int product_id) {
   return this.jdbcTemplate.queryForObject( "select * from product where product_id=? group by product_name", 
    new ProductMapper(),product_id);
 }
 
  public List<Product> findOneProductAllStyle(String product_name) {
   return this.jdbcTemplate.query( "select * from product where product_name=? group by product_style ", 
    new ProductMapper(),product_name);
 }

 public Product findOneTypeOneProduct(String product_name) {
  return this.jdbcTemplate.queryForObject( "select * from product where product_name=?", 
   new ProductMapper(),product_name);
}
 
 public List<Product> findOneProductAllSize(String product_style,String product_name) {
   return this.jdbcTemplate.query( "select * from product where product_style=? and product_name=?", 
    new ProductMapper(),product_style,product_name);
 }
/*!-------------------------------------抓商品的價錢 ---------------------------------------!*/ 
 public Product findThisProductPrice(String product_name,String product_style) {
  return this.jdbcTemplate.queryForObject( "select * from product where product_name=? and product_style=?", 
   new ProductMapper(),product_name,product_style);
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
         
         return Product;

     }
 }

 public int insert(Product Product) throws SQLException{
  return jdbcTemplate.update(
    "insert into product (product_id,product_name, product_desc,product_price,product_stock,product_photo,product_style) values(?,?,?,?,?,?,?)",
    Product.getProductId(),Product.getProductName(), Product.getProductDesc(),Product.getProductPrice(),
    Product.getProductStock(),Product.getProductPhoto(), Product.getProductStyle());
 }

 public int insertToType(Product product){
  KeyHolder keyHolder = new GeneratedKeyHolder();
  String sql = "insert into product (product_name, product_desc,product_price,product_stock,product_photo,product_style) values(?,?,?,?,?,?)";
  jdbcTemplate.update(connection -> {
    PreparedStatement ps = connection
      .prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
      ps.setString(1,product.getProductName());
      ps.setString(2,product.getProductDesc());
      ps.setInt(3,product.getProductPrice());
      ps.setInt(4,product.getProductStock());
      ps.setString(5,product.getProductPhoto());
      ps.setString(6,product.getProductStyle());
      return ps;
    }, keyHolder);
    Number key = keyHolder.getKey();
    return key.intValue(); 
 }
 
 public int update(Product Product) {
  return jdbcTemplate.update(
    "update product set product_name=?, product_desc=?, product_price=?, product_stock=?, product_photo=?, product_style=? where product_id =?",
    Product.getProductName(), Product.getProductDesc(),Product.getProductPrice(),
    Product.getProductStock(),Product.getProductPhoto(), Product.getProductStyle(), Product.getProductId());
 }

 public int delete(String product_name) {
  return jdbcTemplate.update(
    "delete from product where product_name =?", product_name);
 }

 public int deleteProductStyle(String product_style) {
  return jdbcTemplate.update(
    "delete from product where product_style =?", product_style);
 }

}
