package com.example.demo.replyTextMessage;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Supplier;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.entity.Buyer;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Text;

import org.springframework.beans.factory.annotation.Autowired;
public class ExchangePickMoney implements Supplier<TextMessage> {
    private BuyerDAO buyerDAO;
    private String buyer_id;
    private Buyer buyer;
    private int pickmoney;
    private int pickpoint;
    @Autowired
    public ExchangePickMoney(BuyerDAO buyerDAO,String buyer_id){
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
            if(pickpoint==100){
                pickpoint-=100;
                pickmoney+=10;
             }
             else if(pickpoint>100){
                pickpoint-=100;
                pickmoney+=10;
             }
        int pickpo = buyer.getPickpoint();
           buyer.setPickmoney(pickmoney);
           buyer.setPickpoint(pickpoint);
          // int newPickMoney=buyer.getPickmoney();
           buyerDAO.update(buyer);
        if (pickpo>100){
            return new TextMessage("您兌換過後的購物金總額為"+buyer.getPickmoney()+"元,"+"剩餘賴皮指數為"+buyer.getPickpoint()+"點");
        }else{
            return new TextMessage("您的賴皮指數不足");
        }
       // final Text text1 = Text.builder().text("PickMoney:").text(""+pickmoney).build();
        
      
    }
}
