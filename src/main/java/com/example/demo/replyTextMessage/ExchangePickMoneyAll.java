package com.example.demo.replyTextMessage;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Supplier;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.entity.Buyer;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Text;

import org.springframework.beans.factory.annotation.Autowired;
public class ExchangePickMoneyAll implements Supplier<TextMessage> {
    private BuyerDAO buyerDAO;
    private String buyer_id;
    private Buyer buyer;
    private int pickmoney;
    private int pickpoint;
    @Autowired
    public ExchangePickMoneyAll(BuyerDAO buyerDAO,String buyer_id){
        this.buyerDAO = buyerDAO;
        this.buyer_id=buyer_id;
        
    
    }
    public Buyer retrieveOneBuyer() throws SQLException {
        return buyerDAO.findOne(buyer_id);
    }
    
    
    @Override
    public TextMessage get() {
      buyer = new Buyer();
        try{
            buyer = retrieveOneBuyer();
        }catch(SQLException e){
            System.out.println(e);
        }
            pickpoint=buyer.getPickpoint();
            pickmoney=buyer.getPickmoney();
            
            if(pickpoint>=100){
                int q =pickpoint/100;
                pickpoint-=100*q;
                pickmoney+=10*q;
             }
            
           buyer.setPickmoney(pickmoney);
           buyer.setPickpoint(pickpoint);
          
           buyerDAO.update(buyer);
      
        return new TextMessage("您兌換過後的購物金總額為"+buyer.getPickmoney()+"元,"+"剩餘賴皮指數為"+buyer.getPickpoint()+"點");
      
    }
}
