package com.example.demo.flex;
import static java.util.Arrays.asList;

import java.net.URI;
import java.sql.SQLException;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Icon;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import com.example.demo.dao.ReplyDAO;
import com.example.demo.entity.Reply;

public class QAFlexMessage implements Supplier<FlexMessage> {
    private ReplyDAO replyDAO;
    private Reply reply;
    private int seller_id;
    
    private Reply allquestion;
    private Reply x;
    
    public QAFlexMessage(ReplyDAO replyDAO, int seller_id) {
        this.replyDAO = replyDAO;
        this.seller_id = seller_id;
       
    }
    public List<Reply> retrieveReplies() throws SQLException{
        return replyDAO.findAll();
     }

     public Reply retrieveOneReply(int seller_id) throws SQLException{
        seller_id =1;
        Reply r = replyDAO.findOne(seller_id);
        
        return r;  
     }


    @Override
    public FlexMessage get(){
        List<Reply> allquestion = new ArrayList<>();
        List<FlexMessage> flex = new ArrayList<>();
        List<Bubble> bubble = new ArrayList<>();
       
        try {
            allquestion = retrieveReplies();
            reply = retrieveOneReply(seller_id);
        } catch (SQLException e) {
            System.out.println(e);
        }
       

     for(Reply x : allquestion){
        final Box bodyBlock = createBodyBlock(x);
        final Box footerBlock = createFooterBlock(x);
            bubble.add( Bubble.builder()
               .body(bodyBlock)
               .footer(footerBlock)
               .build());
     }
     final Carousel carousel =Carousel.builder().contents(bubble).build();        
        return new FlexMessage("賴皮客服", carousel);
    }

    private Box createFooterBlock(Reply x){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();

        final Button callAction = Button
        .builder()
        .style(ButtonStyle.LINK)
        .height(ButtonHeight.SMALL)
        .action(new MessageAction("Answer", x.getReplyAnswer()))
        .build();
        
        final Separator separator = Separator.builder().build();
        

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer,separator, callAction))
                  .build();
    }

    private Box createBodyBlock(Reply x) {
       
        final Text title =
                Text.builder()
                    .text("Question:")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();

        

        final Box info = createInfoBox(x);

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, info))
                  .build();
    }

    private Box createInfoBox(Reply x) {
     
        final Box question = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text(""+x.getReplyQuestion())
                            .weight(TextWeight.BOLD)
                            .color("#aaaaaa")
                            .size(FlexFontSize.LG)
                            .flex(1)
                            .build()
                ))
                .build();
       
        

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .margin(FlexMarginSize.LG)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(question))
                  .build();
    }

 

}