package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dao.SellerDAO;
import com.example.demo.entity.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerRestController {
    @Autowired
    private SellerDAO sellerDAO;

    //賣家資訊
    @GetMapping(value={"/SellerSet/{id}"})
    public Seller retrieveOneBuyer(@PathVariable int id) throws SQLException{
        System.out.println("in SellerSet spring");
        return sellerDAO.findOne(id);  
     }
     @CrossOrigin
     @PostMapping(value = "/SellerSignUp")
     public void retrieveQAadd(@RequestBody Seller seller) throws SQLException {
        System.out.println("in orderlist Add spring");
        System.out.println(seller);
        System.out.println("STATUS:"+seller.getSellerAccount());
        sellerDAO.insert(seller);
     }
    
     //賣家login
    @GetMapping(value={"/SellerLogIn/{account}/{password}"})
     public Seller retrieveLogin(@PathVariable("account") String account, @PathVariable("password") String password) throws SQLException {
        System.out.println("in SellerLogIn/{account}/{password}");
        System.out.println("account:"+account+" password:"+password);
        Seller result = sellerDAO.login(account,password);  
        System.out.println("result:"+result.getSellerPassword());
        return result;
     }
}