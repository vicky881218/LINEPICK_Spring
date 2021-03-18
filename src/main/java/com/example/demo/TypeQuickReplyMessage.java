package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.sql.SQLException;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;

import com.example.demo.dao.TypeDAO;
import com.example.demo.entity.Type;

public class TypeQuickReplyMessage implements Supplier<Message> {
    private TypeDAO typeDAO;
    private int seller_id;

    public TypeQuickReplyMessage(TypeDAO typeDAO){
        this.typeDAO = typeDAO;

    }

    public List<Type> retrieveSellerAllType() throws SQLException {
        seller_id = 1;
        System.out.println("here!!!!!!!!");
        System.out.println(typeDAO);
        System.out.println("---");
        System.out.println(typeDAO.findSellerAll(seller_id));
        return typeDAO.findSellerAll(seller_id);
    }

    // public int countAllType() throws SQLException {
    // List<Type> allTypes = typeDAO.findAll();
    // return allTypes.size();
    // }

    @Override
    public Message get() {
        List<Type> alltype = new ArrayList<>();
        List<QuickReplyItem> items = new ArrayList<>();
        // System.out.println("!!!!!!!!");
        // System.out.println(alltype);
        // Type type = new Type();
        try {
            alltype = retrieveSellerAllType();            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        for(Type x : alltype){
            items.add(
                QuickReplyItem.builder().action(new MessageAction(x.getTypeName(), x.getTypeName())).build());
         }

        final QuickReply quickReply = QuickReply.items(items);

        return TextMessage.builder().text("點選您想搜尋的分類").quickReply(quickReply).build();
    }
}
