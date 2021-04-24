package com.example.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductType;

@Repository
public class ProductTypeDAODB implements ProductTypeDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ProductType findOne(int product_type_id) {
    ProductType aProductType = jdbcTemplate.queryForObject(
            "select product_type_id, type_id, product_id from product_type where product_type_id = ?",
            new ProductTypeMapper(), product_type_id);
            return aProductType;
 }

    public List<ProductType> findAll() {
        return this.jdbcTemplate.query(
                "select product_type_id, type_id, product_id from product_type",
                new ProductTypeMapper());
    }

    /*用typeId找這個Type對應的所有product---------------------------------------------------------------------------------------------*/
    //從typeName找對應的所有商品(分類)
    public List<ProductType> findOneTypeAllProduct(int type_id) {
        return this.jdbcTemplate.query(
                "Select * FROM type, product_type,product where type.type_id = product_type.type_id and product_type.product_id = product.product_id and type.type_id=?",
                new ProductTypeMapper(),type_id);
    }
    /*---------------------------------------------------------------------------------------------*/

    //找同一個type_id內的所有produt_id
    public List<ProductType> findProuductType(int type_id) {
        return this.jdbcTemplate.query(
                "select product_type_id, type_id, product_id from product_type where type_id = ?",
                new ProductTypeMapper(),type_id);
    }

    private static final class ProductTypeMapper implements RowMapper<ProductType> {

        public ProductType mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductType ProductType = new ProductType();
            ProductType.setProductTypeId(rs.getInt("product_type_id"));
            ProductType.setTypeId(rs.getInt("type_id"));
            ProductType.setProductId(rs.getInt("product_id"));
            return ProductType;
   
        }
    }
   
    public int insert(ProductType ProductType) throws SQLException{
     return jdbcTemplate.update(
       "insert into ProductType (product_type_id,type_id, product_id) values(?,?,?)",
       ProductType.getProductTypeId(),ProductType.getTypeId(), ProductType.getProductId());
    }
    
    public int update(ProductType ProductType) {
     return jdbcTemplate.update(
       "update ProductType set product_type_id=?,type_id=?, product_id=? where product_type_id =?",
       ProductType.getProductTypeId(),ProductType.getTypeId(), ProductType.getProductId());
    }
   
    public int delete(int product_type_id) {
     return jdbcTemplate.update(
       "delete from product_type where product_type_id =?", product_type_id);
    }
}
