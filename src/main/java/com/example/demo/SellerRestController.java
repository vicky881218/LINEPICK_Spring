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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerRestController {
    @Autowired
    private SellerDAO sellerDAO;

    //賣家資訊
    @CrossOrigin
//    @GetMapping(value={"/{id}"})
    @GetMapping(value={"/SellerSet/{id}"})
    public Seller retrieveOneBuyer(@PathVariable int id) throws SQLException{
        System.out.println("in SellerSet spring");
        return sellerDAO.findOne(id);  
     }

     //更改賣家資料庫
     @PutMapping(value = "/SellerEdit")
     public void retrieveBuyerInformationUpdate(@RequestBody Seller seller) throws SQLException {
        System.out.println("in SellerEdit spring");
        System.out.println("seller:"+seller);
        sellerDAO.update(seller);
     }

}
