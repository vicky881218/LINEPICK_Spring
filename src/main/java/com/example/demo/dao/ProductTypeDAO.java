package com.example.demo.dao;

import com.example.demo.entity.ProductType;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ProductTypeDAO {

    public ProductType findOne(int product_type_id);
    public List<ProductType> findAll();
    public ProductType findProuductTypeByProduct(int product_id);
    public List<ProductType> findProuductType(int type_id);
    public List<ProductType> findOneTypeAllProduct(int type_id);
    public List<ProductType> findProductTypeIdByName(String product_name);
    public int insert(ProductType ProductType) throws SQLException;
    public int update(ProductType ProductType);
    public int delete(int product_type_id);
}