package com.example.demo.replyTextMessage;

import java.sql.SQLException;
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

    public BuyerInformation(BuyerDAO buyerDAO,String text, String buyerId) throws SQLException {
        this.buyerDAO = buyerDAO;
        buyer = new Buyer();
        buyer.setBuyerId(buyerId);
        System.out.println("here is buyer_id");
        System.out.println(buyerId);
        buyer.setBuyerName(text);
        System.out.println("here is text");
        System.out.println(text);
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
        return new TextMessage(text1.toString());
    }


}
