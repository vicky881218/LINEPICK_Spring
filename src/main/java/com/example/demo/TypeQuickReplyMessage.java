package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.sql.SQLException;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
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
        return typeDAO.findSellerAll(seller_id);
    }


    @Override
    public Message get() {
        List<Type> alltype = new ArrayList<>();
        List<QuickReplyItem> items = new ArrayList<>();
        try {
            alltype = retrieveSellerAllType();            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        for(Type x : alltype){
            items.add(
                QuickReplyItem.builder()
                              .action(PostbackAction.builder()
                                                    .label(x.getTypeName())
                                                    .text(x.getTypeName())
                                                    .data(x.getTypeName())
                                                    .build())
                                        .build());
         }

        final QuickReply quickReply = QuickReply.items(items);

        return TextMessage.builder().text("點選您想搜尋的分類").quickReply(quickReply).build();
    }
}
