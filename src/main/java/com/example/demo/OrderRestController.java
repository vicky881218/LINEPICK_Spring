package com.example.demo;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.OrderListDAO;
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
    

    @GetMapping(value={"/Orderlist/{orderListStatus}"})
    @CrossOrigin
    public List<OrderList> retrieveOrderlistsFinish(@PathVariable("orderListStatus") String orderListStatus) throws SQLException{
        System.out.println("in /Orderlist/:orderlistStatus spring");
        System.out.println("orderlistStatus:"+orderListStatus);

        return orderlistDAO.findSellerFinishOrder(orderListStatus);
     }

     @GetMapping(value={"/Orderlist/{orderListStatus}/{orderListId}"})
    @CrossOrigin
    public OrderList retrieveOrderlistContent(@PathVariable("orderListId") int orderListId, @PathVariable("orderListStatus") String orderListStatus) throws SQLException{
        System.out.println("in /Orderlist/:orderListId spring");
        System.out.println("orderListId:"+orderListId);
        
        return orderlistDAO.findSellerOrderContent(orderListId, orderListStatus);
     }

}
