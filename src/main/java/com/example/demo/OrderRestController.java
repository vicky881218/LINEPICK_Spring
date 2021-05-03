package com.example.demo;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.OrderInfoDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OrderRestController {
    @Autowired
    private OrderListDAO orderlistDAO;
    @Autowired
    private BuyerDAO buyerDAO;
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    
    @CrossOrigin
    @GetMapping(value={"/Orderlist/{orderListStatus}"})
    
    public List<OrderInfo> retrieveOrderlistsFinish(@PathVariable("orderListStatus") String orderListStatus) throws SQLException{
        System.out.println("in /Orderlist/:orderListStatus spring");
        System.out.println("orderListStatus:"+orderListStatus);
        return orderInfoDAO.findSellerOrder(orderListStatus);
     }

     @CrossOrigin
    @GetMapping(value={"/OrderlistContent/{orderListId}"})
    public List<OrderInfo> retrieveOrderlistContent(@PathVariable("orderListId") int orderListId) throws SQLException{
        System.out.println("in /OrderlistContent/:orderListStatus/id spring");
        System.out.println("orderListId:"+orderListId);
        
        return orderInfoDAO.findSellerOrderBuyerContent(orderListId);
     }

}
