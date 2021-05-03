package com.example.demo;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.OrderInfoDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ReplyDAO;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.OrderList;
import com.example.demo.entity.Reply;

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

public class OrderRestController {
    @Autowired
    private OrderListDAO orderlistDAO;
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private ReplyDAO replyDAO;
    
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

     @CrossOrigin
     @PutMapping(value = "/OrderStatus")
     public void retrieveOrderStatus(@RequestBody OrderList orderList) throws SQLException {
        System.out.println("in orderlist Add spring");
        System.out.println(orderList);
        System.out.println("STATUS:"+orderList.getOrderListStatus());
        orderlistDAO.update(orderList);
     }

   @CrossOrigin
   @GetMapping(value={"/Reply"})
   public List<Reply> retrieveAllReplyQA() throws SQLException{
       System.out.println("in Home spring");
       return replyDAO.findAll();  
    }

    @CrossOrigin
    @GetMapping(value={"/ReplyContent/{replyId}"})
    public Reply retrieveQAContent(@PathVariable("replyId") int replyId) throws SQLException{
        System.out.println("in /ReplyContent/:replyId spring");
        System.out.println("replyId:"+replyId);
        return replyDAO.findOneSeller(replyId);
     }

    @CrossOrigin
     @PostMapping(value = "/QAadd")
     public void retrieveQAadd(@RequestBody Reply reply) throws SQLException {
        System.out.println("in orderlist Add spring");
        System.out.println(reply);
        System.out.println("STATUS:"+reply.getReplyAnswer());
        replyDAO.insert(reply);
     }

     @CrossOrigin
     @PutMapping(value = "/ServiceEdit")
     public void retrieveServiceQuestion(@RequestBody Reply reply) throws SQLException {
        System.out.println("in Reply Add spring");
        System.out.println(reply);
        System.out.println("STATUS:"+reply.getReplyQuestion());
        replyDAO.update(reply);
     }
}
