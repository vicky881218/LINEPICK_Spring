package com.example.demo.replyTextMessage;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Supplier;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.entity.Buyer;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Text;

import org.springframework.beans.factory.annotation.Autowired;

public class BuyerInformation implements Supplier<TextMessage>{
    private BuyerDAO buyerDAO;
    private Buyer buyer;
    
    @Autowired

    public BuyerInformation(BuyerDAO buyerDAO,String buyer_name, String buyerId) throws SQLException {
        this.buyerDAO = buyerDAO;
        buyer = new Buyer();
        buyer.setBuyerId(buyerId);
        System.out.println("here is buyer_id");
        System.out.println(buyerId);
        buyer.setBuyerName(buyer_name);
        System.out.println("here is text");
        System.out.println(buyer_name);
        System.out.println(buyerDAO);
        buyerDAO.insert(buyer);
        
    }

    public Buyer retrieveOneBuyer(String buyer_id,String userId) throws SQLException {
        buyer_id = userId;
        return buyerDAO.findOne(buyer_id);
    }

    @Override
    public TextMessage get() {
        final Text text1 = Text.builder().text("userId:").text(buyer.getBuyerId()).build();
        //System.out.println(); 
        return new TextMessage(buyer.getBuyerName()+"，註冊成功~");
        // return new TextMessage("瑄瑄，註冊成功~");
    }


}
