package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dao.SellerDAO;
import com.example.demo.entity.Seller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value={"/SellerSet/{id}"})
    public Seller retrieveOneBuyer(@PathVariable int id) throws SQLException{
        System.out.println("in SellerSet spring");
        return sellerDAO.findOne(id);  
     }

     /*
     //新增進購物車的資料庫
     @PutMapping(value = "/CartAdd")
     public void retrieveBuyerInformationUpdate(@RequestBody Cart cart) throws SQLException {
        System.out.println("in CartAdd spring");
        System.out.println("cart:"+cart);
        System.out.println("buyer.getBuyerName():"+cart.getBuyerId());
        cartDAO.insert(cart);
     }
     
     //checkout更改買家資訊
     @PutMapping(value = "/BuyerInformation")
     public void retrieveBuyerInformationUpdate(@RequestBody Buyer buyer) throws SQLException {
        System.out.println("in BuyerInformation Add spring");
        System.out.println(buyer);
        System.out.println("buyer.getBuyerName():"+buyer.getBuyerName());
        buyerDAO.update(buyer);
     }

     //delete購物車
     @DeleteMapping(value = "/CartDeleted/{id}")
     public void retrieveBuyerInformationUpdate(@PathVariable int id) throws SQLException {
        System.out.println("in /CartDeleted/ spring");
        System.out.println("id:"+id);
        cartDAO.delete(id);
     }
     */
}
