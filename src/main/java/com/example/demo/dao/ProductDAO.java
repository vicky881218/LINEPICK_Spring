package com.example.demo.dao;

import com.example.demo.entity.Product;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ProductDAO {
    public int insert(Product Product) throws SQLException;
    public List<Product> findAll();
    public List<Product> findOneByName(String product_name);
    public List<Product> findOneTypeAllProduct(int product_id);
    public List<Product> findOneProductAllStyle(String product_name);
    public List<Product> findOneProductAllSize(String product_style);
    public Product findOne(int product_id);
    public int update(Product product);
    public int delete(String product_id);
}
