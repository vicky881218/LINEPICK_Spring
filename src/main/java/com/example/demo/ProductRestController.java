package com.example.demo;

import java.sql.SQLException;

import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    @Autowired
    private ProductDAO productDAO;
    
    @GetMapping(value={"/SingleProduct/{id}"})
    public Product retrieveOneProduct(@PathVariable("id") String id) throws SQLException{
        //product_id = 1;
        System.out.println("p:"+id);
        int product_id_int = Integer.parseInt(id);
        return productDAO.findOne(product_id_int);  
     }
     
     @GetMapping(value={"/SingleProduct"})
    public Product retrieveOneProduct() throws SQLException{
        int product_id = 1;
        System.out.println("test");
        //System.out.println("p:"+id);
        //int product_id_int = Integer.parseInt(id);
        return productDAO.findOne(product_id);  
     }
}
